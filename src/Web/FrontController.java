package Web;

import Core.ParameterBag;
import Web.Controller.Abstract;
import Web.ControllerResponse.Exception;
import Web.ViewRenderer.Html;
import Web.ViewRenderer.Json;
import Web.ViewRenderer.Raw;
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

                if (matched.getResponseType() == null)
                {
                    matched.setResponseType(request.isAjax() ? "json" : "html");
                }

                Abstract controller = matched.getControllerName().newInstance();
                controller.setFrontController(this);
                controller.setRouteMatch(matched);

                try
                {
                    String action = WordUtils.capitalizeFully(matched.getAction().replaceAll("[-/]", " ").trim());
                    action = action.replaceAll(" ", "");

                    if (action.isEmpty())
                    {
                        action = "Index";
                    }

                    params.put("_controller", matched.getControllerName().getName());
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
                                actionMethod = matched.getControllerName().getDeclaredMethod(action, ParameterBag.class);
                                controllerResponse = (Web.ControllerResponse.Abstract) actionMethod.invoke(controller, params);
                            }
                            catch (NoSuchMethodException ignore)
                            {
                                actionMethod = matched.getControllerName().getDeclaredMethod(action);
                                controllerResponse = (Web.ControllerResponse.Abstract) actionMethod.invoke(controller);
                            }
                        }
                        catch (InvocationTargetException e)
                        {
                            throw (java.lang.Exception) e.getCause();
                        }

                        if (controllerResponse == null)
                        {
                            matched = router.getServerErrorRouteMatch();
                            breakLoop = false;
                        }
                        else
                        {
                            // Doing some finalization for the controller...
                            controller.postDispatch(controllerResponse);
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
            }
            while (!breakLoop && count++ < 100);

            if (!breakLoop || controllerResponse == null)
            {
                throw new java.lang.Exception("Unable to resolve the route path to a controller response.");
            }

            // Saving the session to the database before generating the response...
            session.save();
            Core.App.log("Response: %s", controllerResponse.getClass().getName());

            /*if (controllerResponse instanceof Redirect)
            {
                return returnHttpRedirect((Redirect) controllerResponse);
            }*/

            controllerResponse.containerParams.put("section", matched.getSection());
            Web.ViewRenderer.Abstract renderer;

            switch (matched.getResponseType())
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
