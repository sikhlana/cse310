package Core.Entity;

import Core.EntityManager;
import com.j256.ormlite.misc.BaseDaoEnabled;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public abstract class Abstract<T, ID> extends BaseDaoEnabled<T, ID>
{
    public Abstract(Class<? extends EntityManager.Base> manager)
    {
        setDao(EntityManager.getManagerInstance(manager));
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
}
