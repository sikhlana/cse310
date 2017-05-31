package Core.Entity;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "orders", daoClass = Core.EntityManager.Order.class)
public class Order extends Abstract<Order, Integer>
{

}
