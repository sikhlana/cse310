package Web;

import Core.Entity.Abstract;
import Web.TemplateFunctions.AdminLink;
import Web.TemplateFunctions.Container;
import Web.TemplateFunctions.Js;
import Web.TemplateFunctions.Link;
import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class Template
{
    private static Configuration cfg = new Configuration(new Version(2, 3, 20));
    private static boolean configured = false;

    private String templateName;
    private HashMap<String, Object> params = new HashMap<>();

    public Template(String templateName)
    {
        this.templateName = templateName;

        if (!configured)
        {
            try
            {
                File dir = new File("./resources/templates");
                cfg.setDirectoryForTemplateLoading(dir);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            cfg.setDefaultEncoding(Core.App.ENCODING);
            cfg.setLocale(Locale.US);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configured = true;

            for (Functions fn : Functions.values())
            {
                Helpers.defaultParams.put(fn.name(), fn.model);
            }
        }
    }

    public Template(String templateName, Map<String, Object> params)
    {
        this(templateName);
        this.params.putAll(params);
    }

    public Template setParam(String key, Object value)
    {
        params.put(key, value);
        return this;
    }

    public String render()
    {
        try
        {
            StringWriter writer = new StringWriter();

            freemarker.template.Template template = cfg.getTemplate(templateName + ".ftl");
            params.putAll(Helpers.defaultParams);
            template.process(prepareParams(params), writer);

            return writer.toString();
        }
        catch (IOException | TemplateException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> prepareParams(Map<String, Object> params)
    {
        Map<String, Object> out = new HashMap<>();

        for (Map.Entry<String, Object> entry : params.entrySet())
        {
            out.put(entry.getKey(), prepareParam(entry.getValue()));
        }

        return out;
    }

    private List prepareParams(List params)
    {
        List out = new LinkedList();

        for (Object item : params)
        {
            out.add(prepareParam(item));
        }

        return out;
    }

    private Collection prepareParams(Collection params)
    {
        Collection out;

        try
        {
            out = params.getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            return params;
        }

        for (Object item : params)
        {
            out.add(prepareParam(item));
        }

        return out;
    }

    private Object prepareParam(Object param)
    {
        if (param == null)
        {
            return null;
        }

        if (param instanceof Abstract)
        {
            param = prepareParams(((Abstract) param).map());
        }

        if (param instanceof Map)
        {
            param = prepareParams((Map) param);
        }
        else if (param instanceof List)
        {
            param = prepareParams((List) param);
        }
        else if (param instanceof Collection)
        {
            param = prepareParams((Collection) param);
        }

        return param;
    }

    public String toString()
    {
        return render();
    }

    enum Functions
    {
        link(new Link()),
        adminlink(new AdminLink()),
        container(new Container()),
        js(new Js()),
        ;

        TemplateMethodModelEx model;

        Functions(TemplateMethodModelEx model)
        {
            this.model = model;
        }
    }

    public static class Helpers
    {
        final public static Map<String, Object> containerParams = new Hashtable<>();
        final public static Map<String, Object> defaultParams = new HashMap<>();

        public static Web.Link getLink(String route, List list) throws TemplateModelException
        {
            switch (list.size())
            {
                case 1:
                    return new Web.Link(route);

                case 2:
                    if (list.get(1) instanceof SimpleHash)
                    {
                        return new Web.Link(route, ((SimpleHash) list.get(1)).toMap());
                    }

                    Core.App.dump(list.get(1));

                    throw new TemplateModelException("Invalid argument specified.");

                case 3:
                    if (list.get(1) instanceof SimpleHash && list.get(2) instanceof SimpleHash)
                    {
                        return new Web.Link(route, ((SimpleHash) list.get(1)).toMap(), ((SimpleHash) list.get(2)).toMap());
                    }

                    throw new TemplateModelException("Invalid argument specified.");

                default:
                    throw new TemplateModelException("Invalid number of arguments passed.");
            }
        }
    }
}
