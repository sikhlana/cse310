package Web.Controller;

import Core.Gate;
import Web.ControllerResponse.Redirect;
import Web.ControllerResponse.View;
import Web.Link;

import java.sql.SQLException;
import java.util.HashMap;

public class Auth extends Abstract
{
    public Web.ControllerResponse.Abstract actionLogin()
    {
        if (fc.getSession().getUser() != null)
        {
            return new Redirect(new Link("index"));
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("redirect", fc.getSession().get("redirect"));
        params.put("error", fc.getSession().get("error"));

        return new View("login", params);
    }

    public Web.ControllerResponse.Abstract actionLoginLogin() throws SQLException
    {
        try
        {
            Gate.auth(
                    fc.getSession(),
                    (String) fc.getRequest().getParam("username").get(0),
                    (String) fc.getRequest().getParam("password").get(0)
            );
        }
        catch (Gate.Exception e)
        {
            fc.getSession().set("error", "Unable to login. User is missing or password mismatch perhaps?");
            return actionLogin();
        }

        String redirect = (String) fc.getSession().get("redirect");
        fc.getSession().delete("redirect");
        return new Redirect(redirect);
    }

    public Redirect actionLogout()
    {
        fc.getSession().kill();
        return new Redirect(new Link("index"));
    }
}
