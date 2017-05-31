package Web.ControllerResponse;

import Web.Link;

public class Redirect extends Abstract
{
    public Link target = null;
    public String basic = null;

    public Redirect(Link target)
    {
        this(target, 307);
    }

    public Redirect(Link target, int code)
    {
        super(code);
        this.target = target;
    }

    public Redirect(String target)
    {
        this(target, 307);
    }

    public Redirect(String target, int code)
    {
        super(code);
        this.basic = target;
    }
}
