package Web.ControllerResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class Abstract
{
    public int code = 200;

    public Map<String, Object> json = new HashMap<>();
    public Map<String, Object> containerParams = new HashMap<>();

    public boolean container = true;

    public Abstract(int code)
    {
        this.code = code;
    }
}
