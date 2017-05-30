package Web.Controller;

import Web.ControllerResponse.AbstractResponse;

public class Error extends AbstractController
{
    public AbstractResponse actionErrorNotFound()
    {
        return new Web.ControllerResponse.Error("Requested page is not found.", 404);
    }

    public AbstractResponse actionErrorServer()
    {
        return new Web.ControllerResponse.Error("An internal server error occurred.", 500);
    }
}
