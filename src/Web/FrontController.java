package Web;

import Core.ParameterBag;
import Web.Controller.AbstractController;
import Web.ControllerResponse.AbstractResponse;
import Web.ControllerResponse.ResponseException;
import com.github.sommeri.less4j.Less4jException;
import com.github.sommeri.less4j.LessCompiler;
import com.github.sommeri.less4j.core.DefaultLessCompiler;
import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.lang3.text.WordUtils;

import java.io.*;
import java.lang.reflect.Method;
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

        try
        {
            session = new Session(this);
            ParameterBag params = new ParameterBag();

            Router router = new Router();
            Router.Match matched = router.match(request, params);

            AbstractResponse controllerResponse = null;
            boolean breakLoop; int count = 0;

            do
            {
                breakLoop = true;

                AbstractController controller = matched.controllerName.newInstance();
                controller.setFrontController(this);
                controller.setRouteMatch(matched);

                try
                {
                    String action = WordUtils.capitalizeFully(matched.action.replaceAll("-", " ").trim());
                    action = action.replaceAll(" ", "");

                    controller.preDispatch(action);
                    action = String.format("action%s", action);

                    try
                    {
                        Method actionMethod;

                        try
                        {
                            actionMethod = matched.controllerName.getDeclaredMethod(action, ParameterBag.class);
                            controllerResponse = (AbstractResponse) actionMethod.invoke(controller, params);
                        }
                        catch (NoSuchMethodException ignore)
                        {
                            actionMethod = matched.controllerName.getDeclaredMethod(action);
                            controllerResponse = (AbstractResponse) actionMethod.invoke(controller);
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
                catch (ResponseException e)
                {
                    controllerResponse = e.response;
                }
                catch (Exception e)
                {
                    e.printStackTrace(System.err);
                    matched = router.getServerErrorRouteMatch();
                    breakLoop = false;
                }
            }
            while (!breakLoop && count++ < 100);

            if (!breakLoop)
            {
                throw new Exception("Unable to resolve the route path to a controller response.");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
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
                    new ByteArrayInputStream(output.getBytes()), (long) output.length()
            );
        }
        catch (Less4jException e)
        {
            return returnBasicErrorHtml(500);
        }
    }

    private NanoHTTPD.Response returnBasicErrorHtml(int code)
    {
        try
        {
            File file = new File(String.format("./static/error/%d.html", code));

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
                    new ByteArrayInputStream(msg.getBytes()), (long) msg.length()
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
