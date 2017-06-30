package Core;

import Core.Entity.User;

import java.sql.SQLException;

public class Gate
{
    public static void auth(Session session, String email, String password, boolean remember) throws SQLException, Exception
    {
        EntityManager.User manager = (EntityManager.User) EntityManager.getManagerInstance(EntityManager.User.class);
        User user = manager.queryForEmail(email);

        if (user == null)
        {
            throw new UserNotFoundException();
        }

        if (!user.password.equals(Hash.generate(password)))
        {
            throw new PasswordMismatchException();
        }

        session.setUser(user);

        if (remember)
        {
            session.setRememberToken(Hash.generateSalt(64));
        }
    }

    public static class Exception extends java.lang.Exception { }
    public static class UserNotFoundException extends Exception { }
    public static class PasswordMismatchException extends Exception { }
}
