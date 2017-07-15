package Web.Controller.Admin;

import Core.Entity.Product;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.Message;
import Web.ControllerResponse.View;

import java.sql.SQLException;
import java.util.List;

public class Pos extends Abstract
{
    public Object actionIndex()
    {
        return new View("admin/pos");
    }

    public Object actionSearch() throws SQLException {
        String sku = fc.getRequest().getParam("sku");

        Core.EntityManager.Product manager = (Core.EntityManager.Product) Core.EntityManager.getManagerInstance(Core.EntityManager.Product.class);

        List<Product> ls = manager.queryForEq("sku",sku);

        if(ls == null || ls.isEmpty()){
            return new Error("No product found");
        }

        Product p = ls.get(0);

        Core.Cart c = Core.Cart.getInstance(fc.getSession());
        Core.Cart.Item item = c.add(p);

        View view = new View("v");

        this.routeMatch.responseType = "json";
        view.json.put("product_id",p.id);
        view.json.put("product_qty",item.count);


        view.json.put("total_price",c.total());
        view.json.put("product_price",p.price);
        view.json.put("product_title",p.title);

        return view;


    }
}

