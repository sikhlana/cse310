package Web.Controller.Admin;

import Web.ControllerResponse.Message;
import Web.ControllerResponse.View;

public class Pos extends Abstract
{
    public Object actionIndex()
    {
        return new View("admin/pos");
    }
}
