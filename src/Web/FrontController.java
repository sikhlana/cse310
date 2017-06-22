package Web;

import Core.ParameterBag;
import Web.Controller.Abstract;
import Web.ControllerResponse.Exception;
import Web.ControllerResponse.Redirect;
import Web.ViewRenderer.Html;
import Web.ViewRenderer.Json;
import Web.ViewRenderer.Raw;
import com.github.sommeri.less4j.Less4jException;
import com.github.sommeri.less4j.LessCompiler;
import com.github.sommeri.less4j.core.DefaultLessCompiler;
import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.lang3.text.WordUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Date;

public class FrontController
{
    private Date time;
    private Request request;
    private Response response;
    private Session session;

    private InvalidRequestException exception;

    FrontController(NanoHTTPD.IHTTPSession session)
    {
        try
        {
            this.request = new Request(session);
        }
        catch (InvalidRequestException e)
        {
            exception = e;
        }

        this.response = new Response(session);
        this.time = new Date();
    }

    NanoHTTPD.Response run()
    {
        if (exception != null)
        {
            return returnBasicErrorHtml(exception.getCode());
        }

        if (request.getPath().endsWith(".css"))
        {
            return returnCssOutput();
        }

        if (request.getPath().endsWith(".css.map"))
        {
            return returnCssMapOutput();
        }

        if (request.getPath().startsWith("/static/"))
        {
            return returnStaticFile(request.getPath().substring(8));
        }

        if (request.getPath().equals("/favicon.ico"))
        {
            return returnStaticFile(request.getPath().substring(1));
        }

        try
        {
            session = new Session(this);
            ParameterBag params = new ParameterBag();

            Router router = new Router();
            Router.Match matched = router.match(request, params);

            Web.ControllerResponse.Abstract controllerResponse = null;
            boolean breakLoop; int count = 0;

            do
            {
                breakLoop = true;

                if (matched.responseType == null)
                {
                    matched.responseType = request.isAjax() ? "json" : "html";
                }

                Abstract controller = matched.controllerName.newInstance();
                controller.setFrontController(this);
                controller.setRouteMatch(matched);

                try
                {
                    String action = WordUtils.capitalizeFully(matched.action.replaceAll("[-/]", " ").trim());
                    action = action.replaceAll(" ", "");

                    params.put("_controller", matched.controllerName.getName());
                    params.put("_action", action);

                    controller.preDispatch(action);
                    action = String.format("action%s", action);

                    try
                    {
                        Method actionMethod;

                        try
                        {
                            try
                            {
                                actionMethod = matched.controllerName.getDeclaredMethod(action, ParameterBag.class);
                                controllerResponse = (Web.ControllerResponse.Abstract) actionMethod.invoke(controller, params);
                            }
                            catch (NoSuchMethodException ignore)
                            {
                                actionMethod = matched.controllerName.getDeclaredMethod(action);
                                controllerResponse = (Web.ControllerResponse.Abstract) actionMethod.invoke(controller);
                            }
                        }
                        catch (InvocationTargetException e)
                        {
                            throw new java.lang.Exception(e.getTargetException());
                        }

                        if (controllerResponse == null)
                        {
                            matched = router.getServerErrorRouteMatch();
                            breakLoop = false;
                        }
                    }
                    catch (NoSuchMethodException e)
                    {
                        matched = router.getNotFoundErrorRouteMatch();
                        breakLoop = false;
                    }
                }
                catch (Exception e)
                {
                    controllerResponse = e.response;
                }
                catch (java.lang.Exception e)
                {
                    Core.App.debug(e);
                    matched = router.getServerErrorRouteMatch();
                    breakLoop = false;
                }
                finally
                {
                    // Doing some finalization for the controller...
                    if (controllerResponse != null)
                    {
                        controller.postDispatch(controllerResponse);
                    }
                }
            }
            while (!breakLoop && count++ < 100);

            if (!breakLoop)
            {
                throw new java.lang.Exception("Unable to resolve the route path to a controller response.");
            }

            // Saving the session to the database before generating the response...
            session.save();

            if (controllerResponse instanceof Redirect)
            {
                return returnHttpRedirect((Redirect) controllerResponse);
            }

            Web.ViewRenderer.Abstract renderer;

            switch (matched.responseType)
            {
                case "html":
                    renderer = new Html(controllerResponse, this);
                    break;

                case "json":
                    renderer = new Json(controllerResponse, this);
                    break;

                default:
                    renderer = new Raw(controllerResponse, this);
            }

            return response.send(renderer);
        }
        catch (java.lang.Exception e)
        {
            Core.App.debug(e);
        }

        return returnBasicErrorHtml(500);
    }

    private NanoHTTPD.Response returnCssOutput()
    {
        try
        {
            File file = new File("./resources/less/app.less");

            LessCompiler compiler = new DefaultLessCompiler();
            LessCompiler.CompilationResult result = compiler.compile(file);

            String output = result.getCss();

            return new Response.HttpResponse(
                    NanoHTTPD.Response.Status.OK, "text/css",
                    new ByteArrayInputStream(App.getBytes(output)), (long) output.length()
            );
        }
        catch (Less4jException e)
        {
            return returnBasicErrorHtml(500);
        }
    }

    private NanoHTTPD.Response returnCssMapOutput()
    {
        try
        {
            File file = new File("./resources/less/app.less");

            LessCompiler compiler = new DefaultLessCompiler();
            LessCompiler.CompilationResult result = compiler.compile(file);

            String output = result.getSourceMap();

            return new Response.HttpResponse(
                    NanoHTTPD.Response.Status.OK, "application/json",
                    new ByteArrayInputStream(App.getBytes(output)), (long) output.length()
            );
        }
        catch (Less4jException e)
        {
            return returnBasicErrorHtml(500);
        }
    }

    private NanoHTTPD.Response returnHttpRedirect(Redirect response)
    {
        String location = response.target == null ? response.basic : response.target.toString();

        NanoHTTPD.Response http = new Response.HttpResponse(
                NanoHTTPD.Response.Status.lookup(response.code), "text/html",
                new ByteArrayInputStream(new byte[]{}), 0L
        );

        http.addHeader("Location", location);
        return http;
    }

    private NanoHTTPD.Response returnStaticFile(String path)
    {
        try
        {
            File file = new File(String.format("./resources/static/%s", path));

            return new Response.HttpResponse(
                    NanoHTTPD.Response.Status.OK, Files.probeContentType(file.toPath()),
                    new FileInputStream(file), file.length()
            );
        }
        catch (IOException e)
        {
            return returnBasicErrorHtml(404);
        }
    }

    private NanoHTTPD.Response returnBasicErrorHtml(int code)
    {
        try
        {
            File file = new File(String.format("./resources/error/%d.html", code));

            return new Response.HttpResponse(
                    NanoHTTPD.Response.Status.lookup(code), "text/html",
                    new FileInputStream(file), file.length()
            );
        }
        catch (FileNotFoundException e)
        {
            String msg = "Service Unavailable.";

            return new Response.HttpResponse(
                    NanoHTTPD.Response.Status.INTERNAL_ERROR, "text/html",
                    new ByteArrayInputStream(App.getBytes(msg)), (long) msg.length()
            );
        }
    }

    public Request getRequest()
    {
        return request;
    }

    public Response getResponse()
    {
        return response;
    }

    public Session getSession()
    {
        return session;
    }

    public Date getTime()
    {
        return time;
    }
}
