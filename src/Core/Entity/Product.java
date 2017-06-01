package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "products", daoClass = Core.EntityManager.Product.class)
public class Product extends Abstract<Product, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String title;

    @DatabaseField(canBeNull = false)
    public int minimum_stock;

    @DatabaseField(canBeNull = false)
    public int stock;

    @DatabaseField(canBeNull = false)
    public String sku;

    @DatabaseField(canBeNull = false)
    public Product.Type type;

    @DatabaseField(canBeNull = false)
    public int rental_tier;

    @DatabaseField(canBeNull = false)
    public String meta;

    @DatabaseField(canBeNull = false, foreign = true)
    public int supplier;

    @DatabaseField(canBeNull = false)
    public double price;

    public Product()
    {
        super(Core.EntityManager.Product.class);
    }

    public enum Type
    {
        game, accessories, console;
    }
}
