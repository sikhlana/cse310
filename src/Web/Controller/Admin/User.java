package Web.Controller.Admin;

import Core.EntityManager;
import Core.ParameterBag;
import Web.ControllerResponse.Exception;
import Web.ControllerResponse.Redirect;
import Web.ControllerResponse.View;
import Web.Link;

import java.sql.SQLException;

public class User extends Abstract
{
    public Object actionIndex() throws SQLException
    {
        return new View("admin/user_list");
    }

    public Object actionAdd() throws SQLException
    {
        return getAddEditResponse(new Core.Entity.User());
    }

    public Object actionEdit(ParameterBag params) throws SQLException
    {
        return getAddEditResponse(getUserOrError((int) params.get("user_id")));
    }

    private Object getAddEditResponse(Core.Entity.User user) throws SQLException
    {
        return new View("admin/user_edit");
    }

    public Object actionSave()
    {
        assertPostOnly();

        return new Redirect(new Link("admin/users"));
    }

    public Object actionDelete(ParameterBag params) throws SQLException
    {
        assertPostOnly();

        Core.Entity.User user = getUserOrError((int) params.get("user_id"));
        user.delete();

        return new Redirect(new Link("admin/users"));
    }

    private Core.Entity.User getUserOrError(int id) throws SQLException
    {
        EntityManager.User manager = (EntityManager.User) EntityManager.getManagerInstance(EntityManager.User.class);
        Core.Entity.User user = manager.queryForId(id);

        if (user == null)
        {
            throw new Exception(notFoundErrorResponse("Requested user not found."));
        }

        return user;
    }
}
