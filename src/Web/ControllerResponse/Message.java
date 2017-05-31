package Web.ControllerResponse;

public class Message extends Abstract
{
    public String message = null;

    public Message(String message)
    {
        super(200);
        this.message = message;
    }
}
