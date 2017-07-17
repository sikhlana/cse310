package Core;

import Core.Entity.Abstract;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class EntityManager extends DaoManager
{
    public static HashMap<String, Object> mapEntity(Abstract entity)
    {
        HashMap<String, Object> map = new HashMap<>();

        for (Field field : entity.getClass().getDeclaredFields())
        {
            try
            {
                map.put(field.getName(), prepareEntityFieldValueForMap(field.getType(), field.get(entity)));
            }
            catch (IllegalAccessException ignored) { }
        }

        return map;
    }

    public static Object prepareEntityFieldValueForMap(Class<?> cls, Object value)
    {
        if (value == null)
        {
            return getNullValueForEntityField(cls);
        }

        if (Abstract.class.isAssignableFrom(cls))
        {
            return ((Abstract) value).map();
        }

        return value;
    }

    public static Object getNullValueForEntityField(Class<?> cls)
    {
        if (cls.isPrimitive())
        {
            return getPrimitiveNullValueForEntityField(cls);
        }

        if (Number.class.isAssignableFrom(cls))
        {
            if (Integer.class.equals(cls))
            {
                return 0;
            }
            if (Float.class.equals(cls))
            {
                return 0.0;
            }
            if (Double.class.equals(cls))
            {
                return 0D;
            }
            if (Long.class.equals(cls))
            {
                return 0L;
            }
            if (Byte.class.equals(cls))
            {
                return (byte) 0;
            }

            throw new IllegalArgumentException("Invalid numeric data type specified.");
        }

        if (String.class.equals(cls) || Enum.class.isAssignableFrom(cls))
        {
            return "";
        }

        if (Boolean.class.equals(cls))
        {
            return false;
        }

        return null;
    }

    private static Object getPrimitiveNullValueForEntityField(Class<?> cls)
    {
        switch (cls.getTypeName())
        {
            case "int":
                return 0;

            case "float":
                return 0.0;

            case "double":
                return 0D;

            case "long":
                return 0L;

            case "byte":
                return (byte) 0;

            case "boolean":
                return false;
        }

        return null;
    }

    private static Map<Class, Base> managers = new Hashtable<>();

    public static Base getManagerInstance(Class<? extends Base> manager) throws SQLException
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

    public static class ProductImage extends Base<Core.Entity.ProductImage, Integer>
    {
        public ProductImage() throws SQLException
        {
            super(Core.Entity.ProductImage.class);
        }

        public ProductImage(ConnectionSource source) throws SQLException
        {
            super(source, Core.Entity.ProductImage.class);
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

        public Core.Entity.User queryForRememberToken(String token) throws SQLException
        {
            List<Core.Entity.User> list = queryForEq("remember_token", token);
            return list.isEmpty() ? null : list.get(0);
        }

        public Core.Entity.User queryForEmail(String email) throws SQLException
        {
            List<Core.Entity.User> list = queryForEq("email", email);
            return list.isEmpty() ? null : list.get(0);
        }
    }
}
