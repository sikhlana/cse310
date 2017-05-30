package Web;

public class Session
{
    private FrontController fc;

    Session(FrontController fc)
    {
        this.fc = fc;
    }

    public String getCsrfToken()
    {
        return null;
    }
}
