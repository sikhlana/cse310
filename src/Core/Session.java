package Core;

import Core.Entity.User;

public interface Session
{
    String getSessionHash();

    Object get(String key);

    void set(String key, Object value);

    void delete(String key);

    void setUser(User user);

    User getUser();

    void kill();

    void save();

    void setRememberToken(String token);
}
