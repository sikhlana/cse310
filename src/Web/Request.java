package Web;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request
{
    private NanoHTTPD.IHTTPSession session;
    private Map<String, String> files = new HashMap<>();

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

        if (session.getMethod().equals(NanoHTTPD.Method.POST))
        {
            try
            {
                session.parseBody(files);
            }
            catch (IOException | NanoHTTPD.ResponseException e)
            {
                throw new InvalidRequestException(405);
            }
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

    public String getClientIp()
    {
        return session.getRemoteIpAddress();
    }

    public List<String> getParam(String key)
    {
        return session.getParameters().get(key);
    }

    public String getFile(String key)
    {
        return files.get(key);
    }

    public boolean isAjax()
    {
        return session.getHeaders().getOrDefault("x-requested-with", "HttpRequest").equals("XMLHttpRequest");
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
