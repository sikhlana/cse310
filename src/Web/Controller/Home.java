package Web.Controller;

import Web.ControllerResponse.Message;

public class Home extends Abstract
{
    public Object actionIndex()
    {
        return new Message("Something");
    }
}
