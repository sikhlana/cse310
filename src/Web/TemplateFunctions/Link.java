package Web.TemplateFunctions;

import Web.Template;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

public class Link implements TemplateMethodModelEx
{
    @Override
    public Object exec(List list) throws TemplateModelException
    {
        if (list.isEmpty())
        {
            throw new TemplateModelException("Invalid number of arguments passed.");
        }

        return Template.Helpers.getLink((String) list.get(0), list);
    }
}
