package Web;

import Core.Entity.Abstract;
import Web.Route.BuildInterface;

import java.util.Map;

public class Link
{
    private Map<String, Object> data = null;
    private Map<String, Object> params = null;

    private String prefix;
    private String action;

    public Link(String route)
    {
        route = route.replaceAll("(^/|/$)", "");
        String pieces[] = route.split("/", 2);

        if (pieces[0].isEmpty())
        {
            prefix = "index";
        }
        else
        {
            prefix = pieces[0];
        }

        if (pieces.length < 2)
        {
            action = "";
        }
        else
        {
            action = pieces[1];
        }
    }

    public Link(String route, Map<String, Object> data)
    {
        this(route);
        this.data = data;
    }

    public Link(String route, Abstract entity)
    {
        this(route, entity.map());
    }

    public Link(String route, Map<String, Object> data, Map<String, Object> params)
    {
        this(route, data);
        this.params = params;
    }

    public Link(String route, Abstract entity, Map<String, Object> params)
    {
        this(route, entity.map(), params);
    }

    public String toString()
    {
        BuildInterface route = getRoute(prefix);

        if (route == null)
        {
            if (prefix.equals("index"))
            {
                return "/";
            }

            return String.format("/%s/%s", prefix, action);
        }

        return route.build(prefix, action, this, data, params);
    }

    public String buildLinkWithIntegerParam(String prefix, String action, Map<String, Object> data, String id, String title)
    {
        if (data == null || !data.containsKey(id))
        {
            return String.format("/%s/%s", prefix, action);
        }

        int idValue = (int) data.get(id);
        String titleValue = (String) data.getOrDefault(title, null);

        return title == null ? String.format("/%s/%d/%s", prefix, idValue, action)
                : String.format("/%s/%s.%d/%s", prefix, titleValue, idValue, action);
    }

    public String buildLinkWithIntegerParam(String prefix, String action, Map<String, Object> data, String id)
    {
        return buildLinkWithIntegerParam(prefix, action, data, id, "");
    }

    private BuildInterface getRoute(String prefix)
    {
        Router.Routes info;

        try
        {
            info = Router.Routes.valueOf(prefix);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }

        if (BuildInterface.class.isAssignableFrom(info.route))
        {
            try
            {
                return (BuildInterface) info.route.newInstance();
            }
            catch (IllegalAccessException | InstantiationException e)
            {
                return null;
            }
        }

        return null;
    }
}
