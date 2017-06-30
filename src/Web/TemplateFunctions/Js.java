package Web.TemplateFunctions;

import Web.Template;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.LinkedList;
import java.util.List;

public class Js implements TemplateMethodModelEx
{
    @Override
    public Object exec(List list) throws TemplateModelException
    {
        if (!Template.Helpers.containerParams.containsKey("includedJs"))
        {
            Template.Helpers.containerParams.put("includedJs", new LinkedList<String>());
        }

        List<String> ls = (List<String>) Template.Helpers.containerParams.get("includedJs");

        for (Object o : list)
        {
            String js = o.toString();

            if (!js.startsWith("//") && !js.startsWith("http://") && !js.startsWith("https://"))
            {
                js = "/static/js/" + js;
            }

            ls.add(js);
        }

        return "";
    }
}
