package Web.Controller;

import Web.FrontController;

abstract public class AbstractController
{
    private FrontController fc;

    public void setFrontController(FrontController fc)
    {
        this.fc = fc;
    }
}
