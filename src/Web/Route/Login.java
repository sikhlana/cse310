package Web.Route;

import Core.ParameterBag;
import Web.Controller.Auth;
import Web.Router;

public class Login implements MatchInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params)
    {
        return new Router.Match(Auth.class, "login/" + path, "login");
    }
}
