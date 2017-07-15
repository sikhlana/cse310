package Core.Entity;

import Core.Cart;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.LinkedList;

@DatabaseTable(tableName = "orders", daoClass = Core.EntityManager.Order.class)
public class Order extends Abstract<Order, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    public User user;

    @DatabaseField(canBeNull = false)
    public Order.Status status;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    public LinkedList<Cart.Item> product_list;

    @DatabaseField(canBeNull = false)
    public Date created_at;

    @DatabaseField(canBeNull = false)
    public double total_amount;

    @DatabaseField(canBeNull = true)
    public double discount;

    public Order()
    {
        super(Core.EntityManager.Order.class);
    }

    public Invoice createInvoice()
    {
        return null;
        // todo
    }

    public enum Status implements FieldEnum
    {
        pending, completed, cancelled, returned;

        @Override
        public String label()
        {
            return StringUtils.capitalize(name());
        }
    }
}
