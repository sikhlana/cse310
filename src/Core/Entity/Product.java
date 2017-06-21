package Core.Entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.HashMap;


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

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    public HashMap<String, Object> meta;

    @DatabaseField(canBeNull = false, foreign = true)
    public Supplier supplier;

    @DatabaseField(canBeNull = false)
    public double price;

    public Product()
    {
        super(Core.EntityManager.Product.class);
    }

    public enum Type
    {
        game, accessories, console
    }
}
