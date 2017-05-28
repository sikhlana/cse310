package Web;

public class Session implements Core.Session
{
    private FrontController fc;

    Session(FrontController fc)
    {
        this.fc = fc;
    }
}
