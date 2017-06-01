package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "suppliers")
public class Supplier extends Abstract<Supplier, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String supplier_name;

    @DatabaseField(canBeNull = false)
    public String phone_number;

    @DatabaseField(canBeNull = false)
    public String email;

    @DatabaseField(canBeNull = false)
    public String supplier_street_1;

    @DatabaseField
    public String supplier_street_2;

    @DatabaseField(canBeNull = false)
    public String supplier_city;

    @DatabaseField(canBeNull = false)
    public String supplier_state;

    @DatabaseField(canBeNull = false)
    public String supplier_zip;

    @DatabaseField(canBeNull = false)
    public String supplier_country;

}
