package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "coupons", daoClass = Core.EntityManager.Coupon.class)
public class Coupon extends Abstract<Coupon, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false, unique = true)
    public String coupon_code;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    public User user;

    @DatabaseField(canBeNull = false)
    public Date started_at;

    @DatabaseField(canBeNull = false)
    public Date ended_at;

    @DatabaseField(canBeNull = false)
    public double discount;

    @DatabaseField(canBeNull = false)
    public int remaining_count;

    public Coupon()
    {
        super(Core.EntityManager.Coupon.class);
    }
}
