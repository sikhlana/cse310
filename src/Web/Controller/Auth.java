package Web.Controller;

import Core.Entity.User;
import Core.EntityManager;
import Core.Gate;
import Core.Hash;
import Web.ControllerResponse.Exception;
import Web.ControllerResponse.Redirect;
import Web.ControllerResponse.View;
import Web.Link;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Auth extends Abstract
{
    @Override
    protected void preDispatchController(String action)
    {
        super.preDispatchController(action);

        if (!action.equals("Logout"))
        {
            if (fc.getSession().getUser() != null)
            {
                throw new Exception(new Redirect(new Link("index"), Redirect.TEMP_REDIRECT));
            }
        }
    }

    public Object actionLogin()
    {
        return new View("login");
    }

    public Object actionLoginLogin() throws SQLException
    {
        assertPostOnly();

        String email = fc.getRequest().getParam("email");
        String password = fc.getRequest().getParam("password");

        if (email == null)
        {
            error("email", "Email cannot be empty.");
        }

        if (password == null)
        {
            error("password", "Password cannot be empty.");
        }

        if (error)
        {
            return actionLogin();
        }

        try
        {
            Gate.auth(fc.getSession(), email, password, fc.getRequest().getParam("remember") != null);
        }
        catch (Gate.Exception e)
        {
            if (e instanceof Gate.UserNotFoundException)
            {
                error("email", "No user found with the given email address.");
            }
            else if (e instanceof Gate.PasswordMismatchException)
            {
                error("password", "Unable to authenticate the user with the given password.");
            }

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
        return new Redirect(new Link("index"));
    }

    public Object actionRegister()
    {
        return new View("register");
    }

    public Object actionRegisterRegister() throws SQLException, NoSuchFieldException, IllegalAccessException
    {
        assertPostOnly();

        HashMap<String, Object> input = input("name", "phone_number", "email", "password");
        String pConfirm = fc.getRequest().getParam("password_confirm");

        for (Map.Entry<String, Object> entry : input.entrySet())
        {
            if (entry.getValue() == null)
            {
                error(entry.getKey(), "This field cannot be empty.");
            }
        }

        EntityManager.User manager = (EntityManager.User) EntityManager.getManagerInstance(EntityManager.User.class);
        Core.Entity.User user = manager.queryForEmail(input.get("email") == null ? "" : (String) input.get("email"));

        if (user != null)
        {
            error("email", "A user already exists with the given email address.");
        }

        if (!pConfirm.equals(input.get("password")))
        {
            error("password", "The password and password confirm did not match.");
        }

        assertHasErrors();

        input.put("password", Hash.generate((String) input.get("password")));

        user = new User();
        setEntityFields(user, input);
        saveEntity(user);

        fc.getSession().setUser(user);

        String redirect = (String) fc.getSession().get("redirect");
        if (redirect == null)
        {
            redirect = (new Link("index")).toString();
        }

        fc.getSession().delete("redirect");
        return new Redirect(redirect);
    }
}
