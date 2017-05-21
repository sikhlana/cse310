package Web;

import java.io.*;
import java.net.SocketAddress;
import java.net.URLDecoder;
import java.util.Hashtable;

public class Request
{
    private float version;
    private String method, path, body = "";
    private Hashtable<String, String> headers, params;

    Request(InputStream stream) throws IOException, RequestException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String cur, cmd[];

        cur = reader.readLine().trim();
        if (cur.length() == 0 || Character.isWhitespace(cur.charAt(0)))
        {
            throw new BadRequestException();
        }

        cmd = cur.split("\\s");
        if (cmd.length != 3)
        {
            throw new BadRequestException();
        }

        if (cmd[2].indexOf("HTTP/") != 0 || cmd[2].indexOf('.') <= 5)
        {
            throw new BadRequestException();
        }

        try
        {
            version = Float.parseFloat(cmd[2].substring(5));
        }
        catch (NumberFormatException e)
        {
            throw new BadRequestException();
        }

        method = cmd[0];
        switch (method)
        {
            case "GET":
            case "HEAD":
            case "POST":
            case "DELETE":
                break;

            default:
                throw new BadRequestException();
        }

        {
            params = new Hashtable<>();
            int idx = cmd[1].indexOf('?');

            if (idx < 0)
            {
                path = decode(cmd[1]).replaceAll("(^/|/$)", "");
            }
            else
            {
                path = decode(cmd[1].substring(0, idx)).replaceAll("(^/|/$)", "");
                parseParams(cmd[1].substring(++idx));
            }
        }

        {
            headers = new Hashtable<>();

            do
            {
                cur = reader.readLine().trim();
                if (cur.equals(""))
                {
                    break;
                }

                int idx = cur.indexOf(':');
                if (idx < 1)
                {
                    throw new BadRequestException();
                }

                setHeader(cur.substring(0, idx), cur.substring(++idx).trim());
            }
            while (true);

            if (headers.isEmpty())
            {
                throw new BadRequestException();
            }
        }

        if (isPost() || isDelete())
        {
            StringBuilder sb = new StringBuilder();

            while (reader.ready())
            {
                sb.append((char) reader.read());
            }

            body = sb.toString().trim();
            parseParams(body);
        }
    }

    private void parseParams(String str) throws UnsupportedEncodingException, BadRequestException
    {
        if (params == null)
        {
            params = new Hashtable<>();
        }

        String[] prms = str.split("&");

        for (String prm : prms)
        {
            String[] temp = prm.split("=");
            switch (temp.length)
            {
                case 1:
                    if (prm.endsWith("="))
                    {
                        setParam(decode(temp[0]), "");
                    }
                    break;

                case 2:
                    setParam(decode(temp[0]), decode(temp[1]));
                    break;

                default:
                    throw new BadRequestException();
            }
        }
    }

    public String getMethod()
    {
        return method;
    }

    public String getPath()
    {
        return path;
    }

    public float getVersion()
    {
        return version;
    }

    public void parseRemoteAddress(SocketAddress address)
    {
        String[] temp = address.toString().substring(1).split(":");

        setHeader("remote-ip", temp[0]);
        setHeader("remote-port", temp[1]);
    }

    public void setHeader(String key, String value)
    {
        headers.put(key.toLowerCase(), value);
    }

    public String getHeader(String header)
    {
        return headers.get(header.toLowerCase());
    }

    public String getHost()
    {
        return getHeader("host");
    }

    public String getClientIp()
    {
        return getHeader("remote-ip");
    }

    public void setParam(String key, String value)
    {
        params.put(key, value);
    }

    public String getParam(String key)
    {
        return params.get(key);
    }

    public String getRawBody()
    {
        return body;
    }

    public boolean isHead()
    {
        return getMethod().equals("HEAD");
    }

    public boolean isGet()
    {
        return getMethod().equals("GET");
    }

    public boolean isPost()
    {
        return getMethod().equals("POST");
    }

    public boolean isDelete()
    {
        return getMethod().equals("DELETE");
    }

    private String decode(String str) throws UnsupportedEncodingException
    {
        return URLDecoder.decode(str, Core.App.ENCODING);
    }

    class RequestException extends Exception
    {
        public int responseCode;

        RequestException(int responseCode)
        {
            this.responseCode = responseCode;
        }
    }

    class BadRequestException extends RequestException
    {
        BadRequestException()
        {
            super(400);
        }
    }
}
