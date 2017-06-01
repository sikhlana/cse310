package Core;

import Core.Entity.Abstract;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

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

    private static HashMap<Class, Base> managers = new HashMap<>();

    public static Base getManagerInstance(Class<? extends Base> manager)
    {
        if (!managers.containsKey(manager))
        {
            try
            {
                managers.put(manager, manager.newInstance());
            }
            catch (InstantiationException | IllegalAccessException ignored) { }
        }

        return managers.get(manager);
    }

    public static abstract class Base<T, ID> extends BaseDaoImpl<T, ID>
    {
        Base(Class<T> dataClass) throws SQLException
        {
            this(Core.App.getDbConnectionSource(), dataClass);
        }

        Base(ConnectionSource source, Class<T> dataClass) throws SQLException
        {
            super(source, dataClass);
        }
    }

    public static class Coupon extends Base<Core.Entity.Coupon, Integer>
    {
        public Coupon() throws SQLException
        {
            super(Core.Entity.Coupon.class);
        }

        Coupon(ConnectionSource source, Class<Core.Entity.Coupon> dataClass) throws SQLException
        {
            super(source, dataClass);
        }
    }

    public static class Invoice extends Base<Core.Entity.Invoice, Integer>
    {
        public Invoice() throws SQLException
        {
            super(Core.Entity.Invoice.class);
        }

        Invoice(ConnectionSource source, Class<Core.Entity.Invoice> dataClass) throws SQLException
        {
            super(source, dataClass);
        }
    }

    public static class Order extends Base<Core.Entity.Order, Integer>
    {
        public Order() throws SQLException
        {
            super(Core.Entity.Order.class);
        }

        Order(ConnectionSource source, Class<Core.Entity.Order> dataClass) throws SQLException
        {
            super(source, dataClass);
        }
    }

    public static class Product extends Base<Core.Entity.Product, Integer>
    {
        public Product() throws SQLException
        {
            super(Core.Entity.Product.class);
        }

        Product(ConnectionSource source, Class<Core.Entity.Product> dataClass) throws SQLException
        {
            super(source, dataClass);
        }
    }

    public static class Rental extends Base<Core.Entity.Rental, Integer>
    {
        public Rental() throws SQLException
        {
            super(Core.Entity.Rental.class);
        }

        Rental(ConnectionSource source, Class<Core.Entity.Rental> dataClass) throws SQLException
        {
            super(source, dataClass);
        }
    }

    public static class Supplier extends Base<Core.Entity.Supplier, Integer>
    {
        public Supplier() throws SQLException
        {
            super(Core.Entity.Supplier.class);
        }

        Supplier(ConnectionSource source, Class<Core.Entity.Supplier> dataClass) throws SQLException
        {
            super(source, dataClass);
        }
    }

    public static class User extends Base<Core.Entity.User, Integer>
    {
        public User() throws SQLException
        {
            super(Core.Entity.User.class);
        }

        User(ConnectionSource source, Class<Core.Entity.User> dataClass) throws SQLException
        {
            super(source, dataClass);
        }
    }
}
