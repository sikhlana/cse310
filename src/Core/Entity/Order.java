package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "orders", daoClass = Core.EntityManager.Order.class)
public class Order extends Abstract<Order, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String product_list;

    @DatabaseField(canBeNull = false)
    public Date created_at;

    @DatabaseField(canBeNull = false)
    public double total_amount;

    @DatabaseField(foreign = true, canBeNull = false)
    public User user;
}
