package Web.Controller.Admin;

import Web.ControllerResponse.Exception;
import Web.ControllerResponse.Redirect;
import Web.Link;

public class Abstract extends Web.Controller.Abstract
{
    @Override
    protected void preDispatchController(String action)
    {
        super.preDispatchController(action);

        if (fc.getSession().getUser() == null)
        {
            fc.getSession().set("redirect", new Link("admin"));
            throw new Exception(new Redirect("login"));
        }

        if (!fc.getSession().getUser().is_staff)
        {
            throw new Exception(noPermissionErrorResponse());
        }
    }
}
