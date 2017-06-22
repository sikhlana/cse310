package Web.Controller;

import Web.ControllerResponse.Error;
import Web.ControllerResponse.Exception;
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

    final public void preDispatch(String action)
    {
        checkCsrfToken(action);
        preDispatchController(action);
    }

    protected void preDispatchController(String action)
    {
        // this will be overridden by child classes...
    }

    final public void postDispatch(Web.ControllerResponse.Abstract controllerResponse)
    {
        postDispatchController(controllerResponse);
    }

    protected void postDispatchController(Web.ControllerResponse.Abstract controllerResponse)
    {
        // this will be overridden by child classes...
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
                throw new java.lang.Exception();
            }
        }
        catch (java.lang.Exception e)
        {
            throw new Exception(new Error("Security error occurred. Please refresh the form and try again.", 401));
        }
    }

    protected Error noPermissionErrorResponse()
    {
        return new Error("You do not have permission to access this page", 403);
    }

    protected Error serverErrorResponse()
    {
        return new Web.ControllerResponse.Error("An internal server error occurred.", 500);
    }

    protected Error notFoundErrorResponse()
    {
        return notFoundErrorResponse("Requested page is not found.");
    }

    protected Error notFoundErrorResponse(String message)
    {
        return new Error(message, 404);
    }
}
