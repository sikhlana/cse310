package Web.ViewRenderer;

import fi.iki.elonen.NanoHTTPD;

public abstract class Abstract
{
    private Web.ControllerResponse.Abstract response;
    private String mimeType = "text/html";

    Abstract(Web.ControllerResponse.Abstract response)
    {
        this.response = response;
    }

    public NanoHTTPD.Response.IStatus getResponseStatus()
    {
        return NanoHTTPD.Response.Status.lookup(response.code);
    }

    public String getMimeType()
    {
        return mimeType;
    }

    abstract public String render();
}
