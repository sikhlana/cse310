package Web.Route.Admin;

import Core.ParameterBag;
import Web.Route.MatchInterface;
import Web.Router;

public class Home implements MatchInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params) {
        return null;
    }
}
