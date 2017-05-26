package Web.ControllerResponse;

public class ResponseException extends Exception
{
    public AbstractResponse response;

    public ResponseException(AbstractResponse response)
    {
        this.response = response;
    }
}
