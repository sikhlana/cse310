package Core;

import Core.Entity.User;

public interface Session
{
    String getSessionHash();

    Object get(String key);

    void set(String key, Object value);

    void delete(String key);

    User getUser();

    void kill();

    void save();
}
