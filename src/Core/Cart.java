package Core;

import Core.Entity.Order;
import Core.Entity.Product;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart
{
    final private List<Cart.Item> cart = new ArrayList<>();

    public static Cart getInstance(Session session)
    {
        if (!session.has("cart"))
        {
            session.set("cart", new Cart());
        }

        return (Cart) session.get("cart");
    }

    public boolean isEmpty()
    {
        return cart.isEmpty();
    }

    public void add(Item item)
    {
        cart.add(item);
    }

    public void remove(Item item)
    {
        cart.remove(item);
    }

    public void remove(int index)
    {
        cart.remove(index);
    }

    public void clear()
    {
        cart.clear();
    }

    public Order createOrder()
    {
        return null;
        // todo
    }

    public class Item implements Externalizable
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

        @Override
        public void writeExternal(ObjectOutput out) throws IOException
        {
            out.writeObject(config);
            out.writeInt(count);
            out.writeInt(product.id);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
        {
            config = (Map<String, Object>) in.readObject();
            count = in.readInt();

            try
            {
                EntityManager.Product manager = (EntityManager.Product) EntityManager.getManagerInstance(EntityManager.Product.class);
                product = manager.queryForId(in.readInt());

                if (product == null)
                {
                    throw new SQLException();
                }
            }
            catch (SQLException e)
            {
                throw new IOException("Unable to resolve product id to an entity.");
            }
        }
    }
}
