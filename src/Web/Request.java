package Web;

import fi.iki.elonen.NanoHTTPD;

import java.util.List;

public class Request
{
    private NanoHTTPD.IHTTPSession session;

    Request(NanoHTTPD.IHTTPSession session) throws InvalidRequestException
    {
        this.session = session;

        switch (session.getMethod())
        {
            case HEAD:
            case GET:
            case POST:
            case DELETE:
                break;

            default:
                throw new InvalidRequestException(405);
        }
    }

    public String getCookie(String key)
    {
        return session.getCookies().read(key);
    }

    public String getPath()
    {
        return session.getUri();
    }

    public List<String> getParam(String key)
    {
        return session.getParameters().get(key);
    }

    public boolean isAjax()
    {
        return session.getHeaders().containsKey("X-Requested-With")
                && session.getHeaders().get("X-Requested-With").equals("XMLHttpRequest");
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
