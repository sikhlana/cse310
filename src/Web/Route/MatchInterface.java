package Web.Route;

import Core.ParameterBag;
import Web.Controller.AbstractController;
import Web.Request;
import Web.Router;

public interface MatchInterface
{
    Router.Match match(String path, Router router, ParameterBag params);
}
