package Core.Entity;

import Core.EntityManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.BaseDaoEnabled;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public abstract class Abstract<T, ID> extends BaseDaoEnabled<T, ID>
{
    public Abstract(Class<? extends EntityManager.Base> manager)
    {
        try
        {
            setDao(EntityManager.getManagerInstance(manager));
        }
        catch (SQLException e)
        {
            Core.App.debug(e);
        }
    }

    public HashMap<String, Object> map()
    {
        return EntityManager.mapEntity(this);
    }

    @Override
    public int create() throws SQLException
    {
        try
        {
            this.getClass().getField("created_at").set(this, new Date());
        }
        catch (NoSuchFieldException | IllegalAccessException ignored) { }

        try
        {
            this.getClass().getField("updated_at").set(this, new Date());
        }
        catch (NoSuchFieldException | IllegalAccessException ignored) { }

        return super.create();
    }

    @Override
    public int update() throws SQLException
    {
        try
        {
            this.getClass().getField("updated_at").set(this, new Date());
        }
        catch (NoSuchFieldException | IllegalAccessException ignored) { }

        return super.update();
    }

    public Dao.CreateOrUpdateStatus save() throws SQLException
    {
        ID id = extractId();
        if (id != null && getDao().idExists(id))
        {
            return new Dao.CreateOrUpdateStatus(false, true, update());
        }
        else
        {
            return new Dao.CreateOrUpdateStatus(true, false, create());
        }
    }

    public interface FieldEnum
    {
        String label();
    }
}
