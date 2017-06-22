package Web.ViewRenderer;

import Web.FrontController;
import fi.iki.elonen.NanoHTTPD;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public abstract class Abstract
{
    protected FrontController fc;
    protected Web.ControllerResponse.Abstract response;

    Abstract(Web.ControllerResponse.Abstract response, FrontController fc)
    {
        this.response = response;
        this.fc = fc;
    }

    public NanoHTTPD.Response.IStatus getResponseStatus()
    {
        return NanoHTTPD.Response.Status.lookup(response.code);
    }

    abstract public String getMimeType();

    abstract public String render();

    public InputStream getStream()
    {
        return new ByteArrayInputStream(Web.App.getBytes(render()));
    }

    public long getContentLength()
    {
        return 0L;
    }
}
