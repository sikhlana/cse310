package Web;

import Web.ViewRenderer.AbstractRenderer;
import fi.iki.elonen.NanoHTTPD;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Response
{
    private NanoHTTPD.IHTTPSession session;

    Response(NanoHTTPD.IHTTPSession session)
    {
        this.session = session;
    }

    public void setHeader()
    {

    }

    public void setCookie(String name, String value, int expires)
    {
        session.getCookies().set(name, value, expires);
    }

    NanoHTTPD.Response send(AbstractRenderer renderer)
    {

    }

    public class HttpResponse extends NanoHTTPD.Response
    {
        private HttpResponse(NanoHTTPD.Response.IStatus status, String mimeType, InputStream data, long totalBytes)
        {
            super(status, mimeType, data, totalBytes);
        }
    }
}
