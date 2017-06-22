package Web;

import Web.ViewRenderer.Abstract;
import Web.ViewRenderer.Html;
import Web.ViewRenderer.Json;
import fi.iki.elonen.NanoHTTPD;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Response
{
    private NanoHTTPD.IHTTPSession session;
    private Map<String, String> headers = new HashMap<>();

    Response(NanoHTTPD.IHTTPSession session)
    {
        this.session = session;
    }

    public void setHeader(String key, String value)
    {
        setHeader(key, value, false);
    }

    public void setHeader(String key, String value, boolean overwrite)
    {
        if (overwrite || !headers.containsKey(key))
        {
            headers.put(key, value);
        }
    }

    public void setCookie(String name, String value, int expires)
    {
        session.getCookies().set(new Cookie(name, value, expires));
    }

    public void unsetCookie(String name)
    {
        session.getCookies().delete(name);
    }

    NanoHTTPD.Response send(Abstract renderer)
    {
        HttpResponse http = new HttpResponse(
                renderer.getResponseStatus(), renderer.getMimeType(), renderer.getStream(), renderer.getContentLength()
        );

        for (Map.Entry<String, String> pair : headers.entrySet())
        {
            http.addHeader(pair.getKey(), pair.getValue());
        }

        if (renderer instanceof Html || renderer instanceof Json)
        {
            http.setGzipEncoding(true);
        }

        session.getCookies().unloadQueue(http);
        return http;
    }

    public static class HttpResponse extends NanoHTTPD.Response
    {
        public HttpResponse(NanoHTTPD.Response.IStatus status, String mimeType, InputStream data, long totalBytes)
        {
            super(status, mimeType, data, totalBytes);
        }
    }

    private class Cookie extends NanoHTTPD.Cookie
    {
        Cookie(String key, String value, int expires)
        {
            super(key, value, expires);
        }

        public String getHTTPHeader()
        {
            return String.format("%s; path=/", super.getHTTPHeader());
        }
    }
}
