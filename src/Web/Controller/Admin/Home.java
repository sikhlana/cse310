package Web.Controller.Admin;

import Web.ControllerResponse.View;

public class Home extends Abstract
{
    public Object actionIndex()
    {
        return new View("admin/dashboard");
    }
}
