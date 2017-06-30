package Web.Controller.Admin;

import Core.EntityManager;
import Core.ParameterBag;
import Web.ControllerResponse.Exception;
import Web.ControllerResponse.Redirect;
import Web.ControllerResponse.View;
import Web.Link;

import java.sql.SQLException;

public class Rental extends Abstract
{
    public Object actionIndex() throws SQLException
    {
        return new View("admin/rental_list");
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
        return new View("admin/rental_edit");
    }

    public Object actionSave() throws SQLException
    {
        assertPostOnly();

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
