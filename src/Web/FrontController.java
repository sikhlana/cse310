package Web;

import Core.ParameterBag;
import fi.iki.elonen.NanoHTTPD;

import java.io.*;
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

        try
        {
            session = new Session(this);
            ParameterBag params = new ParameterBag();

            Router router = new Router();
            Router.Match matched = router.match(request, params);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }

        return returnBasicErrorHtml(500);
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
                    new ByteArrayInputStream(msg.getBytes()), msg.length()
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
