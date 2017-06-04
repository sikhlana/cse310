package Core.Entity;

import Core.App;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.*;


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

    public Array ProductNameSearch(String s){
        try {


            Connection con = App.getDb().getConnection();
//            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            ResultSet rs = stmt.executeQuery("SELECT TITLE FROM PRODUCT WHERE TITLE LIKE '%"+s+"%'");

            PreparedStatement statement = con.prepareStatement("SELECT TITLE FROM PRODUCT WHERE TITLE LIKE '%"+s+"%'");
            ResultSet rs = statement.executeQuery();

            return rs.getArray("TITLE");

        }catch(SQLException sqle){
            return null;
        }
    }


}
