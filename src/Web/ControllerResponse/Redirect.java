package Web.ControllerResponse;

import Web.Link;

public class Redirect extends Abstract
{
    public Link target;

    public Redirect(Link target)
    {
        this(target, 307);
    }

    public Redirect(Link target, int code)
    {
        super(code);
        this.target = target;
    }
}
