package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "products")
public class Product extends Abstract<Product, Integer> {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String name;

    @DatabaseField(canBeNull = false)
    public int minimum_stock;

    @DatabaseField(canBeNull = false)
    public int stock;

    @DatabaseField(canBeNull = false)
    public char product_sku;

    @DatabaseField(canBeNull = false)
    public String product_type;

    @DatabaseField(canBeNull = false)
    public int rental_tier;

    @DatabaseField
    public String product_meta;

    @DatabaseField(canBeNull = false, foreign = true)
    public int supplier;

    @DatabaseField(canBeNull = false)
    public double price;
}
