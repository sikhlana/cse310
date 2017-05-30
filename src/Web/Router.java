package Web;

import Core.*;
import Web.Controller.AbstractController;
import Web.Route.*;

public class Router
{
    public Match match(Request request, ParameterBag params)
    {
        String routePath = getRoutePath(request);

        if (routePath.isEmpty())
        {
            routePath = "index";
        }

        Routes info;

        try
        {
            info = Routes.valueOf(routePath);
        }
        catch (IllegalArgumentException re)
        {
            return new Match(Web.Controller.Error.class, "error-not-found");
        }

        MatchInterface route;

        try
        {
            route = info.route.newInstance();
        }
        catch (IllegalAccessException | InstantiationException ie)
        {
            return new Match(Web.Controller.Error.class, "error-server");
        }

        return route.match(routePath, this, params);
    }

    public String getRoutePath(Request request)
    {
        return request.getPath().replaceFirst("^/", "");
    }

    public static class Match
    {
        final public Class<? extends AbstractController> controllerName;
        final public String action;
        final public String section;

        public String responseType = "html";

        public Match(Class<? extends AbstractController> controllerName, String action)
        {
            this(controllerName, action, null);
        }

        public Match(Class<? extends AbstractController> controllerName, String action, String section)
        {
            this.controllerName = controllerName;
            this.action = action;
            this.section = section;
        }
    }

    enum Routes
    {
        index(Home.class),
        login(Login.class),
        logout(Logout.class);

        final public Class<? extends MatchInterface> route;

        Routes(Class<? extends MatchInterface> route)
        {
            this.route = route;
        }
    }
}
