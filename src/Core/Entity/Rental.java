package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.lang3.StringUtils;

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
    public Rental.Status status;

    @DatabaseField(canBeNull = false)
    public Date rented_at;

    @DatabaseField
    public Date returned_at;

    public Rental()
    {
        super(Core.EntityManager.Rental.class);
    }

    public enum Tier implements Abstract.FieldEnum
    {
        zero, one, two, three;

        @Override
        public String label()
        {
            return String.format("Tier %d", ordinal());
        }
    }

    public enum Status implements FieldEnum
    {
        active, ended, expired;

        @Override
        public String label()
        {
            return StringUtils.capitalize(name());
        }
    }
}
