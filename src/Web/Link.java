package Web;

import Core.App;
import Core.Entity.Abstract;
import Web.Route.BuildInterface;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
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

    public Link setParams(Map<String, Object> params)
    {
        if (this.params == null)
        {
            this.params = params;
        }
        else
        {
            this.params.putAll(params);
        }

        return this;
    }

    public Link setParam(String key, Object value)
    {
        if (this.params == null)
        {
            this.params = new HashMap<>();
        }

        this.params.put(key, value);
        return this;
    }

    public String toString()
    {
        BuildInterface route = getRoute(prefix);

        if (route != null)
        {
            try
            {
                String built = route.build(prefix, action, this, data, params);
                if (built != null)
                {
                    if (!built.startsWith("/"))
                    {
                        built = "/" + built;
                    }

                    return built.replaceAll("/{2,}", "/");
                }
            }
            catch (Exception ignored) { }
        }

        if (prefix.equals("index"))
        {
            return "/";
        }

        String out = String.format("/%s/%s", prefix, action).replaceAll("/{2,}", "/");

        if (params == null || params.isEmpty())
        {
            return out;
        }

        out += "?";

        for (Map.Entry<String, Object> entry : params.entrySet())
        {
            out += URLEncoder.encode(entry.getKey()) + "=" + URLEncoder.encode((String) entry.getValue());
        }

        return out.substring(0, out.length() - 1);
    }

    public String buildLinkWithIntegerParam(String prefix, String action, Map<String, Object> data, String id, String title)
    {
        if (data == null || !data.containsKey(id))
        {
            return String.format("/%s/%s", prefix, action);
        }

        int idValue = (int) data.get(id);
        String titleValue = (String) data.getOrDefault(title, null);

        return titleValue == null ? String.format("/%s/%d/%s", prefix, idValue, action)
                : String.format("/%s/%s.%d/%s", prefix, normalizeTitle(titleValue), idValue, action);
    }

    public String buildLinkWithIntegerParam(String prefix, String action, Map<String, Object> data, String id)
    {
        return buildLinkWithIntegerParam(prefix, action, data, id, "");
    }

    private BuildInterface getRoute(String prefix)
    {
        return getRoute(prefix, Router.Routes.class);
    }

    private String normalizeTitle(String str)
    {
        return str.toLowerCase().replaceAll("[^a-z0-9]", "-")
                  .replaceAll("-{2,}", "-").replaceAll("(^-|-$)", "");
    }

    public String build(String action, Class<? extends Enum> routes, Map<String, Object> data, Map<String, Object> params)
    {
        String pieces[] = action.split("/", 2);
        String prefix;

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

        BuildInterface route = getRoute(prefix, routes);
        if (route == null)
        {
            if (prefix.equals("index"))
            {
                return action;
            }
            else
            {
                return prefix + "/" + action;
            }
        }

        return route.build(prefix, action, this, data, params);
    }

    private BuildInterface getRoute(String prefix, Class<? extends Enum> routes)
    {
        Router.Route info = null;

        try
        {
            info = (Router.Route) ((Enum) Enum.valueOf(routes, prefix));
        }
        catch (IllegalArgumentException ignore) { }

        if (info == null)
        {
            try
            {
                info = (Router.Route) ((Enum) Enum.valueOf(routes, "defaultRoute"));
            }
            catch (IllegalArgumentException ignore) { }
        }

        if (info == null)
        {
            return null;
        }

        if (BuildInterface.class.isAssignableFrom(info.getRoute()))
        {
            try
            {
                return (BuildInterface) info.getRoute().newInstance();
            }
            catch (IllegalAccessException | InstantiationException e)
            {
                return null;
            }
        }

        return null;
    }
}
