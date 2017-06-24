package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@DatabaseTable(tableName = "invoices", daoClass = Core.EntityManager.Invoice.class)
public class Invoice extends Abstract<Invoice, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    public User user;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    public Order order;

    @DatabaseField(canBeNull = false)
    public Invoice.Status status;

    @DatabaseField(canBeNull = false)
    public double total_price;

    @DatabaseField(canBeNull = false)
    public double discount;

    @DatabaseField(canBeNull = false)
    public Date created_at;

    @DatabaseField(canBeNull = false)
    public Date paid_at;

    public Invoice()
    {
        super(Core.EntityManager.Invoice.class);
    }

    public enum Status implements FieldEnum
    {
        pending, paid, expired, refunded;

        @Override
        public String label()
        {
            return StringUtils.capitalize(name());
        }
    }
}


