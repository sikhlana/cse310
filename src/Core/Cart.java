package Core;

import Core.Entity.Order;
import Core.Entity.Product;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class Cart
{
    private static Map<Integer, Cart> carts = new Hashtable<>();
    private List<Cart.Item> cart = new ArrayList<>();

    private Core.Entity.User user;

    public static Cart getInstance(Session session)
    {
        if (!carts.containsKey(session.getUser().id))
        {
            carts.put(session.getUser().id, new Cart());
        }

        return carts.get(session.getUser().id);
    }

    public boolean isEmpty()
    {
        return cart.isEmpty();
    }

    public void add(Item item)
    {
        cart.add(item);
    }

    public Item add(Product product)
    {
        for (Item i : cart)
        {
            if (i.product.id == product.id)
            {
                i.count++;
                return i;
            }
        }

        Item item = new Item(product);
        cart.add(item);
        return item;
    }

    public void setUser(Core.Entity.User user){
        this.user = user;
    }

    public Core.Entity.User getUser(){
        return user;
    }

    public boolean remove(Item item)
    {
        return cart.remove(item);
    }

    public boolean remove(int productId)
    {
        for (Item i : cart)
        {
            if (i.product.id == productId)
            {
                cart.remove(i);
                return true;
            }
        }

        return false;
    }

    public Item getItemForProduct(int productId)
    {
        for (Item i : cart)
        {
            if (i.product.id == productId)
            {
                return i;
            }
        }

        return null;
    }

    public void clear()
    {
        cart.clear();
        user = null;
    }

    public double total(){
        double totalPrice = 0;

        for(Item i:cart){
            totalPrice = totalPrice + (i.product.price*i.count);
        }

        return totalPrice;

    }

    public Order createOrder()
    {
        return null;
        // todo
    }

    public ArrayList getProductListForView()
    {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>(cart.size());

        for (Item i : cart)
        {
            HashMap<String, Object> map = new HashMap<>();

            map.put("product", i.product);
            map.put("quantity", i.count);

            list.add(map);
        }

        return list;
    }

    public static class Item
    {
        public Product product;
        public Map<String, Object> config;
        public int count;

        public Item(Product product, Map<String, Object> config, int count)
        {
            this.product = product;
            this.config = config;
            this.count = count;
        }

        public Item(Product product, Map<String, Object> config)
        {
            this(product, config, 1);
        }

        public Item(Product product)
        {
            this(product, new HashMap<>());
        }
    }
}
