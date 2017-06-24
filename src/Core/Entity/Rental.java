package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "rentals", daoClass = Core.EntityManager.Rental.class)
public class Rental extends Abstract<Rental, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    public User user;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    public Product product;

    @DatabaseField(canBeNull = false)
    public Date rented_at;

    @DatabaseField
    public Date returned_at;

    public Rental()
    {
        super(Core.EntityManager.Rental.class);
    }

    public enum Tier
    {
        zero, one, two, three;

        public int value()
        {
            return ordinal();
        }

        public String label()
        {
            return String.format("Tier %d", value());
        }
    }
}
