package Core.Entity;

import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.lang3.StringUtils;

@DatabaseTable(tableName = "product_ratings", daoClass = Core.EntityManager.ProductRating.class)
public class ProductRating extends Abstract<ProductRating, Integer>
{
    public int id;

    public Product product;

    public User user;

    public Rating rating;

    public enum Rating implements FieldEnum
    {
        none, shit, works, great;

        @Override
        public String label()
    {
        return StringUtils.capitalize(name());
    }
    }

    public ProductRating()
    {
        super(Core.EntityManager.ProductRating.class);
    }
}
