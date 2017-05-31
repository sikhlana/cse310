package Web.ControllerResponse;

public class Error extends Abstract
{
    public String error = "An unexpected error occurred.";

    public Error(String message, int code)
    {
        super(code);
        this.error = message;
    }
}
