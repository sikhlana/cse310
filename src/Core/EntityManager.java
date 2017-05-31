package Core;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

public class EntityManager extends DaoManager
{
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
