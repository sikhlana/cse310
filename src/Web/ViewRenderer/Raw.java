package Web.ViewRenderer;

import Web.ControllerResponse.AbstractResponse;

public class Raw extends AbstractRenderer
{
    Raw(AbstractResponse response)
    {
        super(response);
    }

    @Override
    public String render()
    {
        return null;
    }
}
