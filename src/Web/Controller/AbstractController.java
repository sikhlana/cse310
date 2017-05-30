package Web.Controller;

import Web.FrontController;
import Web.Router;

abstract public class AbstractController
{
    private FrontController fc;
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

    }

    protected void checkCsrfToken()
    {

    }
}
