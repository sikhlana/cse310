package Core.Entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "users", daoClass = Core.EntityManager.User.class)
public class User extends Abstract<User, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String name;

    @DatabaseField(canBeNull = false, unique = true)
    public String email;

    @DatabaseField
    public boolean is_staff;

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

    @DatabaseField(defaultValue = "0")
    public int purchase_point;

    @DatabaseField
    public String billing_street_1;
    @DatabaseField
    public String billing_street_2;
    @DatabaseField
    public String billing_city;
    @DatabaseField
    public String billing_state;
    @DatabaseField
    public String billing_zip;
    @DatabaseField
    public String billing_country;

    @DatabaseField
    public String shipping_street_1;
    @DatabaseField
    public String shipping_street_2;
    @DatabaseField
    public String shipping_city;
    @DatabaseField
    public String shipping_state;
    @DatabaseField
    public String shipping_zip;
    @DatabaseField
    public String shipping_country;

    @ForeignCollectionField
    public ForeignCollection<Order> orders;

    @ForeignCollectionField
    public ForeignCollection<Invoice> invoices;

    @ForeignCollectionField
    public ForeignCollection<Rental> rentals;

    public User()
    {
        super(Core.EntityManager.User.class);
    }
}
