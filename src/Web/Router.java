package Web;

import Core.*;
import Web.Controller.Abstract;
import Web.Route.*;
import org.apache.commons.lang3.StringUtils;

public class Router
{
    public Match match(Request request, ParameterBag params)
    {
        return match(getRoutePath(request), Routes.values(), params);
    }

    public Match match(String routePath, Enum[] routes, ParameterBag params)
    {
        String pieces[] = routePath.split("/", 2);
        Route info = null; String prefix; MatchInterface route;

        if (pieces[0].isEmpty())
        {
            prefix = "index";
        }
        else
        {
            prefix = pieces[0].toLowerCase();
        }

        if (pieces.length < 2)
        {
            routePath = "";
        }
        else
        {
            routePath = pieces[1];
        }

        for (Enum en : routes)
        {
            if (prefix.equals(en.name()))
            {
                info = (Route) en;
            }
        }

        if (info == null)
        {
            return getNotFoundErrorRouteMatch();
        }

        try
        {
            route = info.getRoute().newInstance();
        }
        catch (IllegalAccessException | InstantiationException | NullPointerException ie)
        {
            return getServerErrorRouteMatch();
        }

        return route.match(routePath, this, params);
    }

    public Match getNotFoundErrorRouteMatch()
    {
        return new Match(Web.Controller.Error.class, "error-not-found");
    }

    public Match getServerErrorRouteMatch()
    {
        return new Match(Web.Controller.Error.class, "error-server");
    }

    public String resolveActionWithIntegerParam(String routePath, ParameterBag params, String paramName)
    {
        return resolveActionWithIntegerParam(routePath, params, paramName, "");
    }

    public String resolveActionWithIntegerParam(String routePath, ParameterBag params, String paramName, String defaultActionWithParam)
    {
        String pieces[] = routePath.split("/", 2);
        String action = "";

        if (pieces.length == 2)
        {
            action = pieces[1];
        }

        pieces = pieces[0].split("\\.");
        int paramId = Integer.parseInt(pieces[pieces.length - 1]);

        if (pieces.length > 1 || StringUtils.isNumeric(pieces[pieces.length - 1]))
        {
            params.put(paramName, paramId);

            if (action.isEmpty())
            {
                action = defaultActionWithParam;
            }

            return action;
        }

        return routePath;
    }

    public String getRoutePath(Request request)
    {
        return request.getPath().replaceAll("(^/|/$)", "");
    }

    public static class Match
    {
        final public Class<? extends Abstract> controllerName;
        final public String action;
        final public String section;

        public String responseType = null;

        public Match(Class<? extends Abstract> controllerName, String action)
        {
            this(controllerName, action, null);
        }

        public Match(Class<? extends Abstract> controllerName, String action, String section)
        {
            this.controllerName = controllerName;
            this.action = action;
            this.section = section;
        }
    }

    enum Routes implements Route
    {
        index(Home.class),
        login(Login.class),
        logout(Logout.class),
        products(Products.class),
        users(Users.class),
        admin(Administration.class),
        ;

        final public Class<? extends MatchInterface> route;

        Routes(Class<? extends MatchInterface> route)
        {
            this.route = route;
        }

        @Override
        public Class<? extends MatchInterface> getRoute()
        {
            return route;
        }
    }

    public interface Route
    {
        Class<? extends MatchInterface> getRoute();
    }
}
