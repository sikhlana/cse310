package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "invoices", daoClass = Core.EntityManager.Invoice.class)
public class Invoice extends Abstract<Invoice, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(foreign = true, canBeNull = false)
    public User user;

    @DatabaseField(foreign = true, canBeNull = false)
    public Order order;

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
}


