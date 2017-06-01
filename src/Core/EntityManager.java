package Core;

import Core.Entity.Abstract;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.DaoManager;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;

public class EntityManager extends DaoManager
{
    public static HashMap<String, Object> mapEntity(Abstract entity)
    {
        HashMap<String, Object> map = new HashMap<>();

        for (Field field : entity.getClass().getDeclaredFields())
        {
            try
            {
                map.put(field.getName(), field.get(entity));
            }
            catch (IllegalAccessException ignored) { }
        }

        return map;
    }

    static abstract class Base<T, ID> extends BaseDaoImpl<T, ID>
    {
        Base(Class<T> dataClass) throws SQLException
        {
            super(Core.App.getDbConnectionSource(), dataClass);
        }
    }

    public static class Coupon extends Base<Core.Entity.Coupon, Integer>
    {
        public Coupon() throws SQLException
        {
            super(Core.Entity.Coupon.class);
        }
    }

    public static class Customer extends Base<Core.Entity.Customer, Integer>
    {
        public Customer() throws SQLException
        {
            super(Core.Entity.Customer.class);
        }
    }

    public static class Invoice extends Base<Core.Entity.Invoice, Integer>
    {
        public Invoice() throws SQLException
        {
            super(Core.Entity.Invoice.class);
        }
    }

    public static class Order extends Base<Core.Entity.Order, Integer>
    {
        public Order() throws SQLException
        {
            super(Core.Entity.Order.class);
        }
    }

    public static class Product extends Base<Core.Entity.Product, Integer>
    {
        public Product() throws SQLException
        {
            super(Core.Entity.Product.class);
        }
    }

    public static class Rental extends Base<Core.Entity.Rental, Integer>
    {
        public Rental() throws SQLException
        {
            super(Core.Entity.Rental.class);
        }
    }

    public static class Supplier extends Base<Core.Entity.Supplier, Integer>
    {
        public Supplier() throws SQLException
        {
            super(Core.Entity.Supplier.class);
        }
    }

    public static class User extends Base<Core.Entity.User, Integer>
    {
        public User() throws SQLException
        {
            super(Core.Entity.User.class);
        }
    }
}
