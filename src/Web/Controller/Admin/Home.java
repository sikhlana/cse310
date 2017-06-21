package Web.Controller.Admin;

import Web.ControllerResponse.Message;

public class Home extends Abstract
{
    public Message actionIndex()
    {
        return new Message("something");
    }
}
