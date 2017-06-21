package Web.ViewRenderer;

import Web.ControllerResponse.Abstract;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.Message;
import Web.ControllerResponse.View;
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

        if (response instanceof Error)
        {
            templateName = "error";
            params.put("error", ((Error) response).error);
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

        String output = (new Template(templateName, params)).toString();

        if (response.container)
        {
            response.containerParams.putAll(params);
            return renderContainer(output, response.containerParams);
        }
        else
        {
            return output;
        }
    }

    private String renderContainer(String contents, Map<String, Object> params)
    {
        params.put("contents", contents);
        String templateName = (String) params.getOrDefault("containerTemplate", "PAGE_CONTAINER");
        return (new Template(templateName, params)).toString();
    }
}
