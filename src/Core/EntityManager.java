package Core;

import Core.Entity.*;
import Core.Entity.Session;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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

        public Coupon(ConnectionSource source) throws SQLException
        {
            super(source, Core.Entity.Coupon.class);
        }
    }

    public static class Invoice extends Base<Core.Entity.Invoice, Integer>
    {
        public Invoice() throws SQLException
        {
            super(Core.Entity.Invoice.class);
        }

        public Invoice(ConnectionSource source) throws SQLException
        {
            super(source, Core.Entity.Invoice.class);
        }
    }

    public static class Order extends Base<Core.Entity.Order, Integer>
    {
        public Order() throws SQLException
        {
            super(Core.Entity.Order.class);
        }

        public Order(ConnectionSource source) throws SQLException
        {
            super(source, Core.Entity.Order.class);
        }
    }

    public static class Product extends Base<Core.Entity.Product, Integer>
    {
        public Product() throws SQLException
        {
            super(Core.Entity.Product.class);
        }

        public Product(ConnectionSource source) throws SQLException
        {
            super(source, Core.Entity.Product.class);
        }
    }

    public static class Rental extends Base<Core.Entity.Rental, Integer>
    {
        public Rental() throws SQLException
        {
            super(Core.Entity.Rental.class);
        }

        public Rental(ConnectionSource source) throws SQLException
        {
            super(source, Core.Entity.Rental.class);
        }
    }

    public static class Session extends Base<Core.Entity.Session, Integer>
    {
        public Session() throws SQLException
        {
            super(Core.Entity.Session.class);
        }

        public Session(ConnectionSource source) throws SQLException
        {
            super(source, Core.Entity.Session.class);
        }

        public Core.Entity.Session queryForHash(String hash) throws SQLException
        {
            List<Core.Entity.Session> list = this.queryForEq("hash", hash);
            return list.isEmpty() ? null : list.get(0);
        }
    }

    public static class Supplier extends Base<Core.Entity.Supplier, Integer>
    {
        public Supplier() throws SQLException
        {
            super(Core.Entity.Supplier.class);
        }

        public Supplier(ConnectionSource source) throws SQLException
        {
            super(source, Core.Entity.Supplier.class);
        }
    }

    public static class User extends Base<Core.Entity.User, Integer>
    {
        public User() throws SQLException
        {
            super(Core.Entity.User.class);
        }

        public User(ConnectionSource source) throws SQLException
        {
            super(source, Core.Entity.User.class);
        }
    }
}
