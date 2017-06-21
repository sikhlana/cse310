package Web;

import Core.Entity.Abstract;
import Web.TemplateFunctions.*;
import Web.TemplateFunctions.Link;
import freemarker.template.*;
import org.flywaydb.core.internal.dbsupport.Function;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class Template
{
    private static Configuration cfg = new Configuration(new Version(2, 3, 20));
    private static boolean configured = false;
    private static Map<String, Object> defaultParams = new Hashtable<>();

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

            cfg.setDefaultEncoding("UTF-8");
            cfg.setLocale(Locale.US);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configured = true;

            for (Functions fn : Functions.values())
            {
                defaultParams.put(fn.name(), fn.model);
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

            freemarker.template.Template template = cfg.getTemplate(templateName + ".html");
            params.putAll(defaultParams);
            template.process(params, writer);

            return writer.toString();
        }
        catch (IOException | TemplateException e)
        {
            throw new RuntimeException(e);
        }
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

        public static Web.Link getLink(String route, List list) throws TemplateModelException
        {
            switch (list.size())
            {
                case 1:
                    return new Web.Link(route);

                case 2:
                    if (list.get(1) instanceof Abstract)
                    {
                        return new Web.Link(route, (Abstract) list.get(1));
                    }
                    else if (list.get(1) instanceof Map)
                    {
                        return new Web.Link(route, (Map) list.get(1));
                    }

                    throw new TemplateModelException("Invalid argument specified.");

                case 3:
                    if (list.get(1) instanceof Abstract)
                    {
                        return new Web.Link(route, (Abstract) list.get(1), (Map) list.get(2));
                    }
                    else if (list.get(1) instanceof Map)
                    {
                        return new Web.Link(route, (Map) list.get(1), (Map) list.get(2));
                    }

                    throw new TemplateModelException("Invalid argument specified.");

                default:
                    throw new TemplateModelException("Invalid number of arguments passed.");
            }
        }
    }
}
