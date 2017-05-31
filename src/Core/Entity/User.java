package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "users", daoClass = Core.EntityManager.User.class)
public class User extends Abstract
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String name;

    @DatabaseField(canBeNull = false, unique = true)
    public String email;

    @DatabaseField
    public String phone_number;

    @DatabaseField(canBeNull = false)
    public String password;

    @DatabaseField
    public String remember_token;

    @DatabaseField(canBeNull = false)
    public Date created_at;

    @DatabaseField
    public Date updated_at;

    @DatabaseField(foreign = true, foreignColumnName = "user_id", readOnly = true)
    public Customer customer;

    public User()
    {
        created_at = new Date();
        updated_at = new Date();
    }
}
