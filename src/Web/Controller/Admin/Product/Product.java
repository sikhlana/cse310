package Web.Controller.Admin.Product;

import Core.Entity.Rental;
import Core.Entity.Supplier;
import Core.EntityManager;
import Core.ParameterBag;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.Exception;
import Web.ControllerResponse.Redirect;
import Web.ControllerResponse.View;
import Web.Link;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product extends Abstract
{
    public Object actionIndex() throws SQLException
    {
        List<Core.Entity.Product> products = EntityManager.getManagerInstance(EntityManager.Product.class).queryForAll();

        HashMap<String, Object> params = new HashMap<>();
        params.put("products", products);

        return new View("admin/product_list", params);
    }

    public Object actionAdd() throws SQLException
    {
        return getAddEditResponse(new Core.Entity.Product());
    }

    public Object actionEdit(ParameterBag params) throws SQLException
    {
        return getAddEditResponse(getProductOrError((int) params.get("product_id")));
    }

    private Object getAddEditResponse(Core.Entity.Product product) throws SQLException
    {
        List<Supplier> suppliers = EntityManager.getManagerInstance(EntityManager.Supplier.class).queryForAll();
        if (suppliers.isEmpty())
        {
            return new Error("You need to have at least one supplier before you can add any product.");
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("product", product);
        params.put("productTypes", Core.Entity.Product.Type.values());
        params.put("rentalTiers", Rental.Tier.values());
        params.put("suppliers", suppliers);
        params.put("selectedSupplierId", product.supplier == null ? 0 : product.supplier.id);

        return new View("admin/product_edit", params);
    }

    public Object actionSave() throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException
    {
        assertPostOnly();
        Core.Entity.Product product = null;

        String p = fc.getRequest().getParam("product_id");
        if (p != null && !p.isEmpty() && StringUtils.isNumeric(p))
        {
            product = (Core.Entity.Product) EntityManager.getManagerInstance(EntityManager.Product.class).queryForId(Integer.parseInt(p));
        }

        if (product == null)
        {
            product = new Core.Entity.Product();
            product.stock = 0;
            product.meta = new HashMap<>();
        }

        HashMap<String, Object> input = input(
                "title", "description", "minimum_stock",
                "sku", "type", "rental_tier", "price", "supplier_id"
        );

        List<HashMap<String, String>> meta = new ArrayList<>();
        List<String> metaFields = (List) fc.getRequest().params().get("meta.fields.name");
        List<String> metaValues = (List) fc.getRequest().params().get("meta.fields.value");

        if (metaFields != null && metaValues != null)
        {
            if (metaFields.size() != metaValues.size())
            {
                return new Error("The number of meta fields do not match with the number of meta values.");
            }

            for (int i = 0; i < metaFields.size(); i++)
            {
                meta.add(i, new HashMap<>());
                meta.get(i).put("name", metaFields.get(i));
                meta.get(i).put("value", metaValues.get(i));
            }
        }

        product.meta.put("fields", meta);
        setEntityFields(product, input);
        saveEntity(product);

        return new Redirect(new Link("admin/products"));
    }

    public Object actionDelete(ParameterBag params) throws SQLException
    {
        assertPostOnly();

        Core.Entity.Product product = getProductOrError((int) params.get("product_id"));
        product.delete();

        return new Redirect(new Link("admin/products"));
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
