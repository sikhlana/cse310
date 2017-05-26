package Web;

import fi.iki.elonen.NanoHTTPD;

import java.util.List;

public class Request
{
    private NanoHTTPD.IHTTPSession session;

    Request(NanoHTTPD.IHTTPSession session)
    {
        this.session = session;
    }

    public String getCookie(String key)
    {
        return session.getCookies().read(key);
    }

    public List<String> getParam(String key)
    {
        return session.getParameters().get(key);
    }

    public boolean isHead()
    {
        return session.getMethod().equals(NanoHTTPD.Method.HEAD);
    }

    public boolean isGet()
    {
        return session.getMethod().equals(NanoHTTPD.Method.GET);
    }

    public boolean isPost()
    {
        return session.getMethod().equals(NanoHTTPD.Method.POST);
    }

    public boolean isDelete()
    {
        return session.getMethod().equals(NanoHTTPD.Method.DELETE);
    }
}
