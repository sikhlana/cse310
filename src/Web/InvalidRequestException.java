package Web;

public class InvalidRequestException extends Exception
{
    private final int code;

    InvalidRequestException(int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }
}
