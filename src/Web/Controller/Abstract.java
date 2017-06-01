package Web.Controller;

import Web.ControllerResponse.*;
import Web.ControllerResponse.Error;
import Web.FrontController;
import Web.Router;

abstract public class Abstract
{
    protected FrontController fc;
    private Router.Match routeMatch;

    public void setFrontController(FrontController fc)
    {
        this.fc = fc;
    }

    public void setRouteMatch(Router.Match match)
    {
        routeMatch = match;
    }

    public void preDispatch(String action)
    {
        checkCsrfToken(action);
    }

    private void checkCsrfToken(String action)
    {
        if (!fc.getRequest().isPost() || !fc.getRequest().isDelete())
        {
            return;
        }

        try
        {
            String token = fc.getRequest().getParam("_token").get(0);

            if (!fc.getSession().getCsrfToken().equals(token))
            {
                throw new Exception();
            }
        }
        catch (Exception e)
        {
            throw new ResponseException(new Error("Security error occurred. Please refresh the form and try again.", 401));
        }
    }
}
