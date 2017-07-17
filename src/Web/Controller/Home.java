package Web.Controller;

import Web.ControllerResponse.View;

public class Home extends Abstract
{
    public Object actionIndex()
    {
        return new View("public/home");
    }
}
