package Core.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

@DatabaseTable(tableName = "product_images", daoClass = Core.EntityManager.ProductImage.class)
public class ProductImage extends Abstract<ProductImage, Integer>
{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public Product product;

    @DatabaseField(canBeNull = false)
    public String file_hash;

    @DatabaseField(canBeNull = false)
    public int file_size;

    @DatabaseField(canBeNull = false)
    public String file_extension;

    @DatabaseField(canBeNull = false)
    public int image_height;

    @DatabaseField(canBeNull = false)
    public int image_width;

    @DatabaseField(canBeNull = false)
    public int thumb_height;

    @DatabaseField(canBeNull = false)
    public int thumb_width;

    public ProductImage() throws SQLException
    {
        super(Core.EntityManager.ProductImage.class);
    }
}
