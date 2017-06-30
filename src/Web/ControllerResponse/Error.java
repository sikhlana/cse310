package Web.ControllerResponse;

import java.util.Map;

public class Error extends Abstract
{
    public String error = "An unexpected error occurred.";

    public Map<String, String> errors = null;

    public Error(Map<String, String> errors)
    {
        this("An error occurred while validating input.");
        this.errors = errors;
    }

    public Error(String message)
    {
        this(message, 400);
    }

    public Error(String message, int code)
    {
        super(code);
        this.error = message;
    }
}
