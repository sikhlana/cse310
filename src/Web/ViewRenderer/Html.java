package Web.ViewRenderer;

import Web.ControllerResponse.Abstract;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.*;
import Web.FrontController;
import Web.Template;

import java.util.HashMap;
import java.util.Map;

public class Html extends Web.ViewRenderer.Abstract
{
    public Html(Abstract response, FrontController fc)
    {
        super(response, fc);
    }

    @Override
    public String getMimeType()
    {
        return "text/html; charset=UTF-8";
    }

    @Override
    public String render()
    {
        String templateName = null;
        HashMap<String, Object> params = new HashMap<>();

        if (response instanceof Redirect)
        {
            String location = ((Redirect) response).target == null
                    ? ((Redirect) response).basic
                    : ((Redirect) response).target.toString();

            fc.getResponse().setHeader("Location", location, true);
            return "";
        }

        if (response instanceof Error)
        {
            templateName = "error";
            params.put("error", ((Error) response).error);
            params.put("code", response.code);
        }
        else if (response instanceof Message)
        {
            templateName = "message";
            params.put("message", ((Message) response).message);
        }
        else if (response instanceof View)
        {
            templateName = ((View) response).template;
            params.putAll(((View) response).params);
        }

        if (templateName == null)
        {
            throw new RuntimeException("The variable templateName cannot be null.");
        }

        params.put("fc", fc);
        String output = (new Template(templateName, params)).toString();

        if (response.container)
        {
            response.containerParams.put("templateName", templateName);
            response.containerParams.put("fc", fc);

            return renderContainer(output, response.containerParams);
        }
        else
        {
            return output;
        }
    }

    private String renderContainer(String contents, Map<String, Object> params)
    {
        params.putAll(Template.Helpers.containerParams);
        Template.Helpers.containerParams.clear();
        params.put("contents", contents);
        String templateName = (String) params.getOrDefault("containerTemplate", "PAGE_CONTAINER");
        return (new Template(templateName, params)).toString();
    }
}
