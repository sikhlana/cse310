package Core.Entity;

import Core.EntityManager;
import com.j256.ormlite.misc.BaseDaoEnabled;

import java.util.HashMap;

public abstract class Abstract<T, ID> extends BaseDaoEnabled<T, ID>
{
    public HashMap<String, Object> map()
    {
        return EntityManager.mapEntity(this);
    }
}
