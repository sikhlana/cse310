package Web;

import Core.Entity.User;
import Core.EntityManager;
import Core.Hash;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.SQLException;

public class Session implements Core.Session
{
    private FrontController fc;

    private int sessionId;
    private String hash;
    private String csrfToken;
    private String clientIp;
    private JSONObject data;
    private User user;

    private boolean saved = true;
    private boolean killed = false;

    Session(FrontController fc)
    {
        this.fc = fc;

        String cookie = fc.getRequest().getCookie("session");
        Core.Entity.Session session = null;

        try
        {
            if (cookie != null)
            {
                EntityManager.Session manager = new EntityManager.Session();
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

                data = new JSONObject();
                session.data = data.toString();

                fc.getResponse().setCookie("session", session.hash, 1);
                session.create();
            }
            else
            {
                try
                {
                    JSONParser parser = new JSONParser();
                    data = (JSONObject) parser.parse(session.data);
                }
                catch (ParseException e)
                {
                    e.printStackTrace(System.err);
                    data = new JSONObject();
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace(System.err);
        }

        sessionId = session.id;
        hash = session.hash;
        csrfToken = session.token;
        clientIp = session.client_ip;
        user = session.user;

        if (!clientIp.equals(fc.getRequest().getClientIp()))
        {
            throw new RuntimeException("Remote IP does not match with session IP. Spoofed?");
        }
    }

    public String getCsrfToken()
    {
        return csrfToken;
    }

    @Override
    public String getSessionHash()
    {
        return hash;
    }

    @Override
    public Object get(String key)
    {
        return data.get(key);
    }

    @Override
    public void set(String key, Object value)
    {
        saved = false;
        data.put(key, value);
    }

    @Override
    public void delete(String key)
    {
        saved = false;
        data.remove(key);
    }

    @Override
    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public User getUser()
    {
        return user;
    }

    @Override
    public void kill()
    {
        fc.getResponse().unsetCookie("session");
        fc.getResponse().unsetCookie("remember_token");

        try
        {
            EntityManager.Session manager = new EntityManager.Session();
            manager.queryForId(sessionId).delete();
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }

        killed = true;
    }

    @Override
    public void save()
    {
        if (saved && !killed)
        {
            return;
        }

        try
        {
            EntityManager.Session manager = new EntityManager.Session();
            Core.Entity.Session session = manager.queryForId(sessionId);

            session.user = user;
            session.data = data.toString();
            session.update();
            saved = true;
        }
        catch (SQLException e)
        {
            e.printStackTrace(System.err);
        }
    }
}
