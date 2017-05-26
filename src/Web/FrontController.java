package Web;

import fi.iki.elonen.NanoHTTPD;

import java.util.Date;

public class FrontController
{
    private Date time;
    private Request request;
    private Response response;
    private Session session;

    FrontController(NanoHTTPD.IHTTPSession session)
    {
        this.request = new Request(session);
        this.response = new Response(session);

        this.time = new Date();
    }

    NanoHTTPD.Response run()
    {
        session = new Session(this);
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
