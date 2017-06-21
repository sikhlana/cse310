package Core;

import Core.Entity.User;

import java.sql.SQLException;

public class Gate
{
    public static void auth(Session session, String email, String password) throws SQLException
    {
        EntityManager.User manager = new EntityManager.User();
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
    }

    static class Exception extends RuntimeException { }
    static class UserNotFoundException extends Exception { }
    static class PasswordMismatchException extends Exception { }
}
