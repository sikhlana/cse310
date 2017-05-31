package Web;

import Web.ViewRenderer.Abstract;
import fi.iki.elonen.NanoHTTPD;

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

    public void unsetCookie(String name)
    {
        session.getCookies().delete(name);
    }

    NanoHTTPD.Response send(Abstract renderer)
    {
        return null;
    }

    public static class HttpResponse extends NanoHTTPD.Response
    {
        public HttpResponse(NanoHTTPD.Response.IStatus status, String mimeType, InputStream data, long totalBytes)
        {
            super(status, mimeType, data, totalBytes);
        }
    }
}
