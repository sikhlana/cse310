package Web.ViewRenderer;

import Web.ControllerResponse.AbstractResponse;
import Web.Response;
import fi.iki.elonen.NanoHTTPD;

public abstract class AbstractRenderer
{
    private AbstractResponse response;
    private String mimeType = "text/html";

    AbstractRenderer(AbstractResponse response)
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
