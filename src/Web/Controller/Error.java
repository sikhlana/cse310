package Web.Controller;

public class Error extends Abstract
{
    public Web.ControllerResponse.Abstract actionErrorNotFound()
    {
        return new Web.ControllerResponse.Error("Requested page is not found.", 404);
    }

    public Web.ControllerResponse.Abstract actionErrorServer()
    {
        return new Web.ControllerResponse.Error("An internal server error occurred.", 500);
    }
}
