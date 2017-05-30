package Web.ControllerResponse;

public abstract class AbstractResponse
{
    public int code = 200;

    public AbstractResponse(int code)
    {
        this.code = code;
    }
}
