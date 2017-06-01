package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "rentals")
public class Rental extends Abstract<Rental, Integer> {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(foreign = true, canBeNull = false)
    public User user;

    @DatabaseField(foreign = true, canBeNull = false)
    public Product product;

    @DatabaseField(canBeNull = false)
    public Date rental_date;

    @DatabaseField(canBeNull = false)
    public Date return_date;
}
