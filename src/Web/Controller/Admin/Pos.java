package Web.Controller.Admin;

import Core.Entity.*;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.Message;
import Web.ControllerResponse.View;

import java.sql.SQLException;
import java.util.HashMap;
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

    public Object actionSetUser() throws SQLException {
        String userId = fc.getRequest().getParam("user_id");
        Core.EntityManager.User manager = (Core.EntityManager.User) Core.EntityManager.getManagerInstance(Core.EntityManager.User.class);

        Core.Entity.User user = manager.queryForId(Integer.parseInt(userId));

        if(user == null){
            return new Error("No such user found");
        }

        Core.Cart c = Core.Cart.getInstance(fc.getSession());
        c.setUser(user);


        Message m = new Message("User added");
        m.json.put("purchase_points",user.purchase_point);
        return m;
    }

    public Object actionClear(){
        Core.Cart c = Core.Cart.getInstance(fc.getSession());
        c.clear();
        return new Message("Cart Cleared");
    }

    public Object actionCheckout() throws SQLException {
        Core.Cart c = Core.Cart.getInstance(fc.getSession());

        Order order = c.createOrder();

        String orderD = fc.getRequest().getParam("discount");
        float discount = Float.parseFloat(orderD);

        order.status = Order.Status.completed;

        Invoice i = order.createInvoice();
        i.discount = discount;

        saveEntity(i);
        saveEntity(order);

        HashMap<String, Object> params = new HashMap<>();

        params.put("invoice", i);

        return new View("admin/pos_invoice", params);


    }
    public Object actionRedeemPoints(){
        Core.Cart c = Core.Cart.getInstance(fc.getSession());

        if(c.getUser() == null){
            return new Error("Set a user first");
        }

        int points = c.getUser().purchase_point;

        int rem = points/100;
        int discount = rem*10;

        c.getUser().purchase_point -= rem*100;

        Message m = new Message("Purchase points redeemed");

        m.json.put("discount",discount);

        return m;

    }
}

