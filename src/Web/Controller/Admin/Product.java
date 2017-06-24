package Web.Controller.Admin;

import Core.Entity.Rental;
import Core.Entity.Supplier;
import Core.EntityManager;
import Core.ParameterBag;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.Exception;
import Web.ControllerResponse.View;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Product extends Abstract
{
    public Object actionIndex()
    {
        return new View("admin/product_list");
    }

    public Object actionAdd() throws SQLException
    {
        return getAddEditResponse(new Core.Entity.Product());
    }

    public Object actionEdit(ParameterBag params) throws SQLException
    {
        return getAddEditResponse(getProductOrError((int) params.get("id")));
    }

    private Object getAddEditResponse(Core.Entity.Product product) throws SQLException
    {
        List<Supplier> suppliers = EntityManager.getManagerInstance(EntityManager.Supplier.class).queryForAll();
        if (suppliers.isEmpty())
        {
            return new Error("You need to have at least one supplier before you can add any product.", 400);
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("product", product);
        params.put("productTypes", Core.Entity.Product.Type.values());
        params.put("rentalTiers", Rental.Tier.values());
        params.put("suppliers", suppliers);

        return new View("admin/product_edit", params);
    }

    public Object actionSave()
    {
        return null;
    }

    public Object actionDelete()
    {
        return null;
    }

    private Core.Entity.Product getProductOrError(int id) throws SQLException
    {
        EntityManager.Product manager = (EntityManager.Product) EntityManager.getManagerInstance(EntityManager.Product.class);
        Core.Entity.Product product = manager.queryForId(id);

        if (product == null)
        {
            throw new Exception(notFoundErrorResponse("Request product is not found."));
        }

        return product;
    }
}
