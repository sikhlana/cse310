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
        user.password = Hash.generate("04275020", Hash.getGlobalSalt());

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
            ex.printStackTrace(System.err);
        }
    }
}
