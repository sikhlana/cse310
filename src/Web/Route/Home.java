package Web.Route;

import Core.ParameterBag;
import Web.Router;

public class Home implements MatchInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params)
    {
        return new Router.Match(Web.Controller.Home.class, "index");
    }
}
