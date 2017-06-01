package Core.Entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "suppliers", daoClass = Core.EntityManager.Supplier.class)
public class Supplier extends Abstract<Supplier, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String name;

    @DatabaseField(canBeNull = false)
    public String phone_number;

    @DatabaseField(canBeNull = false)
    public String email;

    @DatabaseField(canBeNull = false)
    public String address_street_1;

    @DatabaseField
    public String address_street_2;

    @DatabaseField(canBeNull = false)
    public String address_city;

    @DatabaseField(canBeNull = false)
    public String address_state;

    @DatabaseField(canBeNull = false)
    public String address_zip;

    @DatabaseField(canBeNull = false)
    public String address_country;

    @ForeignCollectionField
    public ForeignCollection<Product> products;

    public Supplier()
    {
        super(Core.EntityManager.Supplier.class);
    }
}
