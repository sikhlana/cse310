package Web.ViewRenderer;

import Web.ControllerResponse.Abstract;
import Web.FrontController;

public class Raw extends Web.ViewRenderer.Abstract
{
    private String mimeType = "application/octet-stream";

    public Raw(Abstract response, FrontController fc)
    {
        super(response, fc);
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    @Override
    public String getMimeType()
    {
        return mimeType;
    }

    @Override
    public String render()
    {
        return null;
    }
}
