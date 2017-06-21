package Web.ControllerResponse;

public class Exception extends RuntimeException
{
    final public Abstract response;

    public Exception(Abstract response)
    {
        this.response = response;
    }
}
