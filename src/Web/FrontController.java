package Web;

import Core.*;
import fi.iki.elonen.NanoHTTPD;

import java.io.ByteArrayInputStream;
import java.net.Socket;
import java.util.Date;

public class FrontController
{
    private Date time;
    private Request request;

    FrontController(NanoHTTPD.IHTTPSession session)
    {
        this.request = new Request(session);
        this.time = new Date();
    }

    Response run()
    {

    }

    public Request getRequest()
    {
        return request;
    }
}
