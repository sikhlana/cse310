package Web;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request
{
    private NanoHTTPD.IHTTPSession session;
    private Map<String, String> files = new HashMap<>();
    private Parameters params;

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

        params = new Parameters(session.getParameters());
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

    public Parameters params()
    {
        return params;
    }

    public String getParam(String key)
    {
        return params().single(key);
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

    public class Parameters
    {
        private Map<String, Object> params = new HashMap<>();

        Parameters(Map<String, List<String>> p)
        {
            for (Map.Entry<String, List<String>> entry : p.entrySet())
            {
                for (String value : entry.getValue())
                {
                    parse(params, entry.getKey(), value);
                }
            }
        }

        private void parse(Map<String, Object> params, String name, String value)
        {
            name = name.replaceAll("\\.{2,}", ".").replaceAll("(^\\.|\\.$)", "");

            if (name.contains("."))
            {
                String pieces[] = name.split("\\.", 2);

                if (pieces.length >= 2)
                {
                    Map<String, Object> map = null;

                    if (params.containsKey(pieces[0]))
                    {
                        if (params.get(pieces[0]) instanceof Map)
                        {
                            map = (Map) params.get(pieces[0]);
                        }
                    }

                    if (map == null)
                    {
                        map = new HashMap<>();
                        params.put(pieces[0], map);
                    }

                    parse(map, pieces[1], value);
                    return;
                }

                name = pieces[0];
            }

            if (params.containsKey(name))
            {
                Object existing = params.get(name);

                if (existing instanceof List)
                {
                    ((List) existing).add(value);
                }
                else
                {
                    List<String> l = new ArrayList<>();

                    l.add((String) existing);
                    l.add(value);

                    params.put(name, l);
                }
            }
            else
            {
                params.put(name, value);
            }
        }

        public Object get(String name)
        {
            return params.get(name);
        }

        public String single(String name)
        {
            Object o = get(name);
            if (o != null && o instanceof String)
            {
                return (String) o;
            }

            return null;
        }
    }
}
