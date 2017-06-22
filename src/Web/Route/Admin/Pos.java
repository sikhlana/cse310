package Web.Route.Admin;

import Core.ParameterBag;
import Web.Route.MatchInterface;
import Web.Router;

public class Pos implements MatchInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params)
    {
        return new Router.Match(Web.Controller.Admin.Pos.class, path, "pos");
    }
}
