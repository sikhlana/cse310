package Web.ControllerResponse;

import Web.Link;

public class Redirect extends Abstract
{
    final public static int MOVED_PERMENANTLY  = 301;
    final public static int SEE_OTHER          = 303;
    final public static int TEMP_REDIRECT      = 307;

    public Link target = null;
    public String basic = null;

    public Redirect(Link target)
    {
        this(target, SEE_OTHER);
    }

    public Redirect(Link target, int code)
    {
        super(code);
        this.target = target;
    }

    public Redirect(String target)
    {
        this(target, SEE_OTHER);
    }

    public Redirect(String target, int code)
    {
        super(code);
        this.basic = target;
    }
}
