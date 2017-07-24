package Web;

import Core.ParameterBag;
import Web.Controller.Abstract;
import Web.Route.*;
import org.apache.commons.lang3.StringUtils;

public class Router
{
    public Match match(Request request, ParameterBag params)
    {
        return match(getRoutePath(request), Routes.class, params);
    }

    public Match match(String routePath, Class<? extends Enum> routes, ParameterBag params)
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

        try
        {
            info = (Route) ((Enum) Enum.valueOf(routes, prefix));
        }
        catch (IllegalArgumentException ignore) { }

        if (info == null)
        {
            try
            {
                info = (Route) ((Enum) Enum.valueOf(routes, "defaultRoute"));
                routePath = prefix + "/" + routePath;
            }
            catch (IllegalArgumentException ignore) { }
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
        if (StringUtils.isNumeric(pieces[pieces.length - 1]))
        {
            int paramId = Integer.parseInt(pieces[pieces.length - 1]);
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
        private Class<? extends Abstract> controllerName;
        private String action;
        private String section;

        private String responseType = null;

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

        public void setControllerName(Class<? extends Abstract> controllerName)
        {
            this.controllerName = controllerName;
        }

        public Class<? extends Abstract> getControllerName()
        {
            return controllerName;
        }

        public void setAction(String action)
        {
            this.action = action;
        }

        public String getAction()
        {
            return action;
        }

        public void setSection(String section)
        {
            this.section = section;
        }

        public String getSection()
        {
            return section == null ? "" : section;
        }

        public void setResponseType(String responseType)
        {
            this.responseType = responseType;
        }

        public String getResponseType()
        {
            return responseType;
        }
    }

    enum Routes implements Route
    {
        index(Home.class),
        login(Login.class),
        register(Register.class),
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
