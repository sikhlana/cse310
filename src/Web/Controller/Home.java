package Web.Controller;

import Web.ControllerResponse.Message;

public class Home extends Abstract
{
    public Web.ControllerResponse.Abstract actionIndex()
    {
        return new Message("Something");
    }
}
