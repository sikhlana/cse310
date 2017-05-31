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

        @Override
        public int update(T data) throws SQLException
        {
            setUpdatedAt(data);
            return super.update(data);
        }

        private void setUpdatedAt(T data)
        {
            try
            {
                data.getClass().getField("updated_at").set(data, new java.util.Date());
            }
            catch (NoSuchFieldException | IllegalAccessException ignored) { }
        }
    }

    public static class User extends Base<Core.Entity.User, Integer>
    {
        public User() throws SQLException
        {
            super(Core.Entity.User.class);
        }
    }

    public static class Customer extends Base<Core.Entity.Customer, Integer>
    {
        public Customer() throws SQLException
        {
            super(Core.Entity.Customer.class);
        }
    }

    public static class Order extends Base<Core.Entity.Order, Integer>
    {
        public Order() throws SQLException
        {
            super(Core.Entity.Order.class);
        }
    }
}
