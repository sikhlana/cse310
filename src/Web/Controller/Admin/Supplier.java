package Web.Controller.Admin;

import Core.EntityManager;
import Core.ParameterBag;
import Web.ControllerResponse.Exception;
import Web.ControllerResponse.Redirect;
import Web.ControllerResponse.View;
import Web.Link;
import com.neovisionaries.i18n.CountryCode;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Supplier extends Abstract
{
    public Object actionIndex() throws SQLException
    {
        List<Core.Entity.Supplier> suppliers = EntityManager.getManagerInstance(EntityManager.Supplier.class).queryForAll();

        HashMap<String, Object> params = new HashMap<>();
        params.put("suppliers", suppliers);

        return new View("admin/supplier_list", params);
    }

    public Object actionAdd() throws SQLException
    {
        return getAddEditResonse(new Core.Entity.Supplier());
    }

    public Object actionEdit(ParameterBag params) throws SQLException
    {
        return getAddEditResonse(getSupplierOrError((int) params.get("supplier_id")));
    }

    private Object getAddEditResonse(Core.Entity.Supplier supplier) throws SQLException
    {
        HashMap<String, Object> params = new HashMap<>();

        params.put("supplier", supplier);
        params.put("countries", CountryCode.values());

        return new View("admin/supplier_edit", params);
    }

    public Object actionSave() throws SQLException, NoSuchFieldException, IllegalAccessException
    {
        assertPostOnly();
        Core.Entity.Supplier supplier = null;

        String p = fc.getRequest().getParam("supplier_id");
        if (p != null && !p.isEmpty() && StringUtils.isNumeric(p))
        {
            supplier = (Core.Entity.Supplier) EntityManager.getManagerInstance(EntityManager.Supplier.class).queryForId(Integer.parseInt(p));
        }

        if (supplier == null)
        {
            supplier = new Core.Entity.Supplier();
        }

        HashMap<String, Object> input = input(
                "name", "address_street_1", "address_street_2", "address_city",
                "address_state", "address_zip", "address_country", "phone_number", "email"
        );

        setEntityFields(supplier, input);
        saveEntity(supplier);

        return new Redirect(new Link("admin/suppliers"));
    }

    public Object actionDelete(ParameterBag params) throws SQLException
    {
        assertPostOnly();

        Core.Entity.Supplier supplier = getSupplierOrError((int) params.get("supplier_id"));
        supplier.delete();

        return new Redirect(new Link("admin/suppliers"));
    }

    private Core.Entity.Supplier getSupplierOrError(int id) throws SQLException
    {
        EntityManager.Supplier manager = (EntityManager.Supplier) EntityManager.getManagerInstance(EntityManager.Supplier.class);
        Core.Entity.Supplier supplier = manager.queryForId(id);

        if (supplier == null)
        {
            throw new Exception(notFoundErrorResponse("Request supplier is not found."));
        }

        return supplier;
    }
}
