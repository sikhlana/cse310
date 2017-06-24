package Web;

import Core.Entity.User;
import Core.EntityManager;
import Core.Hash;

import java.sql.SQLException;
import java.util.HashMap;

public class Session implements Core.Session
{
    private FrontController fc;

    private Core.Entity.Session session;

    private boolean saved = true;
    private boolean killed = false;

    Session(FrontController fc)
    {
        this.fc = fc;
        String cookie = fc.getRequest().getCookie("session");

        try
        {
            if (cookie != null)
            {
                EntityManager.Session manager = (EntityManager.Session) EntityManager.getManagerInstance(EntityManager.Session.class);
                session = manager.queryForHash(cookie);
            }

            if (session == null)
            {
                String remember = fc.getRequest().getCookie("remember_token");
                User user = null;

                try
                {
                    EntityManager.User userManager = new EntityManager.User();
                    user = userManager.queryForRememberToken(remember);
                }
                catch (SQLException ignore) { }

                session = new Core.Entity.Session();

                session.client_ip = fc.getRequest().getClientIp();
                session.hash = Hash.generateSalt(64);
                session.token = Hash.generate(session.hash);
                session.user = user;
                session.data = new HashMap<>();

                fc.getResponse().setCookie("session", session.hash, 1);
                session.create();
            }
        }
        catch (SQLException e)
        {
            Core.App.debug(e);
        }

        if (!session.client_ip.equals(fc.getRequest().getClientIp()))
        {
            throw new RuntimeException("Remote IP does not match with session IP. Spoofed?");
        }
    }

    public String getCsrfToken()
    {
        return session.token;
    }

    @Override
    public String getSessionHash()
    {
        return session.hash;
    }

    @Override
    public Object get(String key)
    {
        return session.data.get(key);
    }

    @Override
    public void set(String key, Object value)
    {
        saved = false;
        session.data.put(key, value.toString());
    }

    @Override
    public void delete(String key)
    {
        saved = false;
        session.data.remove(key);
    }

    @Override
    public void setUser(User user)
    {
        session.user = user;
    }

    @Override
    public User getUser()
    {
        return session.user;
    }

    @Override
    public void kill()
    {
        fc.getResponse().unsetCookie("session");
        fc.getResponse().unsetCookie("remember_token");

        try
        {
            session.delete();
        }
        catch (Exception e)
        {
            Core.App.debug(e);
        }

        killed = true;
    }

    @Override
    public void save()
    {
        if (saved || killed)
        {
            return;
        }

        try
        {
            session.update();
            saved = true;
        }
        catch (Exception e)
        {
            Core.App.debug(e);
        }
    }
}
