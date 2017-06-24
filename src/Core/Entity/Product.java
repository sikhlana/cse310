package Core.Entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;


@DatabaseTable(tableName = "products", daoClass = Core.EntityManager.Product.class)
public class Product extends Abstract<Product, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String title;

    @DatabaseField
    public String description;

    @DatabaseField(canBeNull = false)
    public Integer minimum_stock;

    @DatabaseField(canBeNull = false)
    public Integer stock;

    @DatabaseField(canBeNull = false)
    public String sku;

    @DatabaseField(canBeNull = false)
    public Product.Type type;

    @DatabaseField(canBeNull = false, dataType = DataType.ENUM_INTEGER)
    public Rental.Tier rental_tier;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    public HashMap<String, Object> meta;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public Supplier supplier;

    @DatabaseField(canBeNull = false)
    public Double price;

    public Product()
    {
        super(Core.EntityManager.Product.class);
    }

    public enum Type implements FieldEnum
    {
        game, accessories, console;

        @Override
        public String label()
        {
            return StringUtils.capitalize(name());
        }
    }
}
