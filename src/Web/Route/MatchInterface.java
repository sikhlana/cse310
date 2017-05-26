package Web.Route;

import Core.ParameterBag;
import Web.Controller.AbstractController;
import Web.Request;
import Web.Router;

interface MatchInterface
{
    AbstractController match(String path, Router router, ParameterBag params);
}
