package Web.Controller;

import Core.Gate;
import Web.ControllerResponse.Redirect;
import Web.ControllerResponse.View;
import Web.Link;

import java.sql.SQLException;
import java.util.HashMap;

public class Auth extends Abstract
{
    public Object actionLogin()
    {
        if (fc.getSession().getUser() != null)
        {
            return new Redirect(new Link("index"), Redirect.TEMP_REDIRECT);
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("redirect", fc.getSession().get("redirect"));
        params.put("error", fc.getSession().get("error"));

        fc.getSession().delete("error");
        return new View("login", params);
    }

    public Object actionLoginLogin() throws SQLException
    {
        try
        {
            Gate.auth(
                    fc.getSession(),
                    (String) fc.getRequest().getParam("email").get(0),
                    (String) fc.getRequest().getParam("password").get(0)
            );
        }
        catch (Gate.Exception e)
        {
            fc.getSession().set("error", "Unable to login. User is missing or password mismatch perhaps?");
            return actionLogin();
        }

        String redirect = (String) fc.getSession().get("redirect");
        if (redirect == null)
        {
            redirect = (new Link("index")).toString();
        }

        fc.getSession().delete("redirect");
        return new Redirect(redirect);
    }

    public Redirect actionLogout()
    {
        fc.getSession().kill();
        return new Redirect(new Link("index"), Redirect.SEE_OTHER);
    }
}
