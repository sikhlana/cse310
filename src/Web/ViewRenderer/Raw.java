package Web.ViewRenderer;

import Web.ControllerResponse.Abstract;

public class Raw extends Web.ViewRenderer.Abstract
{
    Raw(Abstract response)
    {
        super(response);
    }

    @Override
    public String render()
    {
        return null;
    }
}
