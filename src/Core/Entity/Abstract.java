package Core.Entity;

import Core.EntityManager;

import java.util.HashMap;

public abstract class Abstract
{
    public HashMap<String, Object> map()
    {
        return EntityManager.mapEntity(this);
    }
}
