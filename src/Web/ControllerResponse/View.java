package Web.ControllerResponse;

import Web.View.Base;

import java.util.HashMap;
import java.util.Map;

public class View extends Abstract
{
    public Class<? extends Base> view = null;

    public String template = "";

    public Map<String, Object> params = new HashMap<>();

    public View(String template)
    {
        super(200);
        this.template = template;
    }

    public View(String template, Map<String, Object> params)
    {
        this(template);

        if (params != null)
        {
            this.params.putAll(params);
        }
    }

    public View(Class<? extends Base> view, String template)
    {
        this(template);
        this.view = view;
    }

    public View(Class<? extends Base> view, String template, Map<String, Object> params)
    {
        this(template, params);
        this.view = view;
    }
}
