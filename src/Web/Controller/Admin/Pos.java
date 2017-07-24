package Web.Controller.Admin;

import Core.Cart;
import Core.Entity.Invoice;
import Core.Entity.Order;
import Core.Entity.Product;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.Message;
import Web.ControllerResponse.Redirect;
import Web.ControllerResponse.View;
import Web.Link;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Pos extends Abstract
{
    public Object actionIndex()
    {
        Cart cart = Cart.getInstance(fc.getSession());
        HashMap<String, Object> params = new HashMap<>();
        params.put("products", cart.getProductListForView());
        params.put("total", cart.total());

        return new View("admin/pos", params);
    }

    public Object actionSearch() throws SQLException {
        String sku = fc.getRequest().getParam("sku");


        Core.EntityManager.Product manager = (Core.EntityManager.Product) Core.EntityManager.getManagerInstance(Core.EntityManager.Product.class);

        List<Product> ls = manager.queryForEq("sku",sku);

        if(ls == null || ls.isEmpty()){
            return new Error("No product found!");
        }

        Product p = ls.get(0);

        Core.Cart c = Core.Cart.getInstance(fc.getSession());
        Core.Cart.Item item = c.add(p);

        HashMap<String, Object> params = new HashMap<>();

        params.put("product", p);
        params.put("quantity", item.count);

        View view = new View("admin/pos/list-item", params);

        this.routeMatch.setResponseType("json");
        view.json.put("product_id",p.id);
        view.json.put("product_qty",item.count);

        view.json.put("product_price",p.price);
        view.json.put("product_title",p.title);

        return basicPosResponse(view);


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
        return basicPosResponse(m);
    }

    public Object actionClear(){
        Core.Cart c = Core.Cart.getInstance(fc.getSession());
        c.clear();
        return basicPosResponse(new Redirect(new Link("admin/pos")));
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

        return basicPosResponse(new View("admin/pos_invoice", params));


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

        return basicPosResponse(m);

    }

    public Object actionDeleteItem()
    {
        assertDeleteOnly();
        Core.Cart c = Core.Cart.getInstance(fc.getSession());

        c.remove(Integer.parseInt(fc.getRequest().getParam("id")));

        return basicPosResponse(new Redirect(new Link("admin/pos")));
    }

    public Object actionSetQty()
    {
        assertPostOnly();
        Core.Cart c = Core.Cart.getInstance(fc.getSession());
        int productId = Integer.parseInt(fc.getRequest().getParam("product_id"));
        int quantity = Integer.parseInt(fc.getRequest().getParam("quantity"));
        boolean removed = false;

        if (quantity < 1)
        {
            removed = true;
            c.remove(productId);
        }
        else
        {
            c.getItemForProduct(productId).count = quantity;
        }


        Redirect response = new Redirect(new Link("admin/pos"));
        response.json.put("removed", removed);
        return basicPosResponse(response);
    }

    private Web.ControllerResponse.Abstract basicPosResponse(Web.ControllerResponse.Abstract response)
    {
        Core.Cart cart = Core.Cart.getInstance(fc.getSession());
        response.json.put("total", cart.total());

        return response;
    }
}

