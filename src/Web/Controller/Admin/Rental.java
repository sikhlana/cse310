package Web.Controller.Admin;

import Core.Entity.*;
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

public class Rental extends Abstract
{
    public Object actionIndex() throws SQLException
    {
        List<Core.Entity.Rental> rentals = EntityManager.getManagerInstance(EntityManager.Rental.class).queryForEq("status", Core.Entity.Rental.Status.active);

        HashMap<String, Object> params = new HashMap<>();
        params.put("rentals", rentals);

        return new View("admin/rental_list",params);
    }

    public Object actionAdd() throws SQLException
    {
        return getAddEditResponse(new Core.Entity.Rental());
    }

    public Object actionEdit(ParameterBag params) throws SQLException
    {
        return getAddEditResponse(getRentalOrError((int) params.get("rental_id")));
    }

    private Object getAddEditResponse(Core.Entity.Rental rental) throws SQLException
    {
        HashMap<String, Object> params = new HashMap<>();

        params.put("rental", rental);

        return new View("admin/rental_edit",params);
    }

    public Object actionSave() throws SQLException, NoSuchFieldException, IllegalAccessException {
        assertPostOnly();
        Core.Entity.Rental rental = null;

        String p = fc.getRequest().getParam("rental_id");
        if (p != null && !p.isEmpty() && StringUtils.isNumeric(p))
        {
            rental = (Core.Entity.Rental) EntityManager.getManagerInstance(EntityManager.Rental.class).queryForId(Integer.parseInt(p));
        }

        if (rental == null)
        {
            rental = new Core.Entity.Rental();
        }

        HashMap<String, Object> input = input(
                "user_id", "product_id", "status","rented_at","returned_at"
        );

        setEntityFields(rental, input);
        saveEntity(rental);

        return new Redirect(new Link("admin/rentals"));
    }

    public Object actionDelete(ParameterBag params) throws SQLException
    {
        assertPostOnly();

        Core.Entity.Rental rental = getRentalOrError((int) params.get("rental_id"));
        rental.delete();

        return new Redirect(new Link("admin/rentals"));
    }

    private Core.Entity.Rental getRentalOrError(int id) throws SQLException
    {
        EntityManager.Rental manager = (EntityManager.Rental) EntityManager.getManagerInstance(EntityManager.Rental.class);
        Core.Entity.Rental rental = manager.queryForId(id);

        if (rental == null)
        {
            throw new Exception(notFoundErrorResponse("Requested rental information not found."));
        }

        return rental;
    }
}
