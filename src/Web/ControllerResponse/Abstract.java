package Web.ControllerResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class Abstract
{
    public int code = 200;

    public Map<String, Object> json = new HashMap<>();

    public Abstract(int code)
    {
        this.code = code;
    }
}
