package Web.Controller.Admin;

import Core.EntityManager;
import Core.Hash;
import Core.ParameterBag;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.Exception;
import Web.ControllerResponse.Redirect;
import Web.ControllerResponse.View;
import Web.Link;
import com.neovisionaries.i18n.CountryCode;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class User extends Abstract
{
    public Object actionIndex() throws SQLException
    {
        List<Core.Entity.User> users = EntityManager.getManagerInstance(EntityManager.User.class).queryForAll();

        HashMap<String, Object> params = new HashMap<>();
        params.put("users", users);
        return new View("admin/user_list",params);
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
        HashMap<String, Object> params = new HashMap<>();

        params.put("user", user);
        params.put("countries", CountryCode.values());
        return new View("admin/user_edit",params);
    }

    public Object actionSave() throws SQLException, NoSuchFieldException, IllegalAccessException {
        assertPostOnly();
        Core.Entity.User user = null;

        String p = fc.getRequest().getParam("user_id");
        if (p != null && !p.isEmpty() && StringUtils.isNumeric(p))
        {
            user = (Core.Entity.User) EntityManager.getManagerInstance(EntityManager.User.class).queryForId(Integer.parseInt(p));
        }

        if (user == null)
        {
            user = new Core.Entity.User();
        }

        HashMap<String, Object> input = input(
                "name", "email", "is_staff", "phone_number",
                "billing_street_1", "billing_street_2", "billing_city", "billing_state",
                "billing_zip","billing_country","shipping_street_1","shipping_street_2","shipping_city",
                "shipping_state","shipping_zip","shipping_country","purchase_point"
        );

        if (fc.getRequest().hasParam("password"))
        {
            input.put("password", Hash.generate(fc.getRequest().getParam("password")));
        }

        setEntityFields(user, input);
        saveEntity(user);

        return new Redirect(new Link("admin/users"));
    }

    public Object actionDelete(ParameterBag params) throws SQLException
    {
        assertDeleteOnly();

        Core.Entity.User user = getUserOrError((int) params.get("user_id"));

        if (user.id == fc.getSession().getUser().id)
        {
            return new Error("You cannot delete your own account.");
        }

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
