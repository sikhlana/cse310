package Core;

import Core.Entity.Abstract;
import Core.Entity.User;

import java.sql.SQLException;

public class Seeder
{
    public static void run()
    {
        // Insert a couple of users...
        User user = new User();

        user.name = "Saif Mahmud";
        user.email = "sikhlana@gmail.com";
        user.phone_number = "(880) 175 7111 189";
        user.password = "c03c2a1841e23d1dc21008bdc95af2a26a801ad9cf1d676fab54fab3dfae69bf";
        user.is_staff = true;

        create(user);
    }

    private static void create(Abstract e)
    {
        try
        {
            e.create();
        }
        catch (SQLException ex)
        {
            Core.App.debug(ex);
        }
    }
}
