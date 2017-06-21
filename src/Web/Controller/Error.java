package Web.Controller;

public class Error extends Abstract
{
    public Web.ControllerResponse.Abstract actionErrorNotFound()
    {
        return notFoundErrorResponse();
    }

    public Web.ControllerResponse.Abstract actionErrorServer()
    {
        return serverErrorResponse();
    }
}
