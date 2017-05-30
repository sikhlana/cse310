package Web.ControllerResponse;

public class ResponseException extends RuntimeException
{
    final public AbstractResponse response;

    public ResponseException(AbstractResponse response)
    {
        this.response = response;
    }
}
