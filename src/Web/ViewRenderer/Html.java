package Web.ViewRenderer;

import Web.ControllerResponse.Abstract;
import Web.FrontController;

public class Html extends Web.ViewRenderer.Abstract
{
    public Html(Abstract response, FrontController fc)
    {
        super(response, fc);
    }

    @Override
    public String getMimeType()
    {
        return "text/html; charset=UTF-8";
    }

    @Override
    public String render()
    {
        return null;
    }
}
