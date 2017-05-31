package Core.Entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "customers", daoClass = Core.EntityManager.Customer.class)
public class Customer
{
    @DatabaseField(id = true)
    public int user_id;

    @DatabaseField(defaultValue = "0")
    public int purchase_points;

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

    @DatabaseField(foreign = true, canBeNull = false)
    public User user;

    @ForeignCollectionField
    public ForeignCollection<Order> orders;
}
