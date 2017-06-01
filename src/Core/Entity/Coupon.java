package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "coupons")
public class Coupon extends Abstract<Coupon, Integer> {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false, unique = true)
    public String coupon_code;

    @DatabaseField(foreign = true, canBeNull = false)
    public User user;

    @DatabaseField(canBeNull = false)
    public Date start_date;

    @DatabaseField(canBeNull = false)
    public Date end_date;

    @DatabaseField(canBeNull = false)
    public double discount;

    @DatabaseField(canBeNull = false)
    public int remaining_count;
}