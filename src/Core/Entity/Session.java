package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "sessions", daoClass = Core.EntityManager.Session.class)
public class Session extends Abstract<Session, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(foreign = true)
    public User user;

    @DatabaseField(canBeNull = false)
    public String client_ip;

    @DatabaseField(canBeNull = false)
    public String hash;

    @DatabaseField(canBeNull = false)
    public String token;

    @DatabaseField
    public String data;

    @DatabaseField
    public Date created_at;

    public Session()
    {
        super(Core.EntityManager.Session.class);
    }
}
