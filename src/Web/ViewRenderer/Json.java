package Web.ViewRenderer;

import Web.ControllerResponse.Abstract;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.Message;
import Web.FrontController;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Json extends Web.ViewRenderer.Abstract
{
    public Json(Abstract response, FrontController fc)
    {
        super(response, fc);
    }

    @Override
    public String getMimeType()
    {
        return "application/json; charset=UTF-8";
    }

    @Override
    public String render()
    {
        HashMap<String, Object> map = new HashMap<>();

        map.put("code", response.code);
        map.put("status", response instanceof Error ? "error" : "success");

        if (response.json != null && !response.json.isEmpty())
        {
            map.put("params", response.json);
        }

        if (response instanceof Message)
        {
            map.put("message", ((Message) response).message);
        }
        else if (response instanceof Error)
        {
            map.put("error", ((Error) response).error);
        }

        return (new JSONObject(map)).toString();
    }
}
