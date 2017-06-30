package Web;

import java.util.HashMap;

public class InputError extends HashMap<String, String>
{
    public InputError()
    {
        super();
    }

    public InputError(String key, String message)
    {
        super();
        put(key, message);
    }
}
