package Web.TemplateFunctions;

import Web.Template;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

public class Container implements TemplateMethodModelEx
{
    @Override
    public Object exec(List list) throws TemplateModelException
    {
        if (list.size() != 2)
        {
            throw new TemplateModelException("Invalid number of arguments passed.");
        }

        Template.Helpers.containerParams.put(list.get(0).toString(), list.get(1).toString());
        return "";
    }
}
