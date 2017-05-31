package Web.ControllerResponse;

public class ResponseException extends RuntimeException
{
    final public Abstract response;

    public ResponseException(Abstract response)
    {
        this.response = response;
    }
}
