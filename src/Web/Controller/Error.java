package Web.Controller;

public class Error extends Abstract
{
    public Object actionErrorNotFound()
    {
        return notFoundErrorResponse();
    }

    public Object actionErrorServer()
    {
        return serverErrorResponse();
    }
}
