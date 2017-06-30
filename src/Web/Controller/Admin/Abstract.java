package Web.Controller.Admin;

import Web.ControllerResponse.Exception;
import Web.ControllerResponse.Redirect;
import Web.Link;

public class Abstract extends Web.Controller.Abstract
{
    @Override
    protected void preDispatchController(String action)
    {
        if (fc.getSession().getUser() == null)
        {
            fc.getSession().set("redirect", fc.getRequest().getPath());
            throw new Exception(new Redirect(new Link("login")));
        }

        if (!fc.getSession().getUser().is_staff)
        {
            throw new Exception(noPermissionErrorResponse());
        }
    }

    @Override
    protected void postDispatchController(Web.ControllerResponse.Abstract controllerResponse)
    {
        controllerResponse.containerParams.put("containerTemplate", "admin/PAGE_CONTAINER");
    }
}
