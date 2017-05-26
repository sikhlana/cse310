package Web;

import fi.iki.elonen.NanoHTTPD;

import java.io.*;
import java.net.SocketAddress;
import java.net.URLDecoder;
import java.util.Hashtable;

public class Request
{
    private NanoHTTPD.IHTTPSession session;

    Request(NanoHTTPD.IHTTPSession session)
    {
        this.session = session;
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
