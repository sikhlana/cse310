package Web;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

            cfg.setDefaultEncoding("UTF-8");
            cfg.setLocale(Locale.US);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configured = true;
        }
    }

    public Template(String templateName, Map<String, Object> params)
    {
        this(templateName);
        this.params.putAll(params);
    }

    public String render()
    {
        try
        {
            StringWriter writer = new StringWriter();

            freemarker.template.Template template = cfg.getTemplate(templateName + ".html");
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
}
