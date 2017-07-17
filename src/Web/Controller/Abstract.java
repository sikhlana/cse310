package Web.Controller;

import Core.EntityManager;
import Web.ControllerResponse.Error;
import Web.ControllerResponse.Exception;
import Web.ControllerResponse.View;
import Web.FrontController;
import Web.InputError;
import Web.Router;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.DatabaseTable;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

abstract public class Abstract
{
    protected FrontController fc;
    protected Router.Match routeMatch;

    protected boolean error = false;

    public void setFrontController(FrontController fc)
    {
        this.fc = fc;
    }

    public void setRouteMatch(Router.Match match)
    {
        routeMatch = match;
    }

    final public void preDispatch(String action)
    {
        checkCsrfToken(action);
        preDispatchController(action);
    }

    protected void preDispatchController(String action)
    {
        // this will be overridden by child classes...
    }

    final public void postDispatch(Web.ControllerResponse.Abstract controllerResponse)
    {
        processAjaxRequestForError();
        passSessionVariablesToParam(controllerResponse);
        postDispatchController(controllerResponse);
    }

    protected void postDispatchController(Web.ControllerResponse.Abstract controllerResponse)
    {
        // this will be overridden by child classes...
    }

    protected void passSessionVariablesToParam(Web.ControllerResponse.Abstract controllerResponse)
    {
        if (controllerResponse instanceof View)
        {
            ((View) controllerResponse).params.put("_error", fc.getSession().get("error"));
            ((View) controllerResponse).params.put("_redirect", fc.getSession().get("redirect"));
        }

        fc.getSession().delete("error");
    }

    protected void processAjaxRequestForError()
    {
        if (fc.getRequest().isPost() && fc.getRequest().isAjax())
        {
            assertHasErrors();
        }
    }

    protected void checkCsrfToken(String action)
    {
        if (!fc.getRequest().isPost() || !fc.getRequest().isDelete())
        {
            return;
        }

        try
        {
            String token = fc.getRequest().getParam("_token");

            if (!fc.getSession().getCsrfToken().equals(token))
            {
                throw new java.lang.Exception();
            }
        }
        catch (java.lang.Exception e)
        {
            throw new Exception(new Error("Security error occurred. Please refresh the form and try again.", 401));
        }
    }

    protected Error noPermissionErrorResponse()
    {
        return new Error("You do not have permission to access this page", 403);
    }

    protected Error serverErrorResponse()
    {
        return new Web.ControllerResponse.Error("An internal server error occurred.", 500);
    }

    protected Error notFoundErrorResponse()
    {
        return notFoundErrorResponse("Requested page is not found.");
    }

    protected Error notFoundErrorResponse(String message)
    {
        return new Error(message, 404);
    }

    protected void assertPostOnly()
    {
        if (!fc.getRequest().isPost())
        {
            throw new Exception(new Error("This page can only be accessed via POST only.", 400));
        }
    }

    protected void assertPutOnly()
    {
        if (!fc.getRequest().isPut())
        {
            throw new Exception(new Error("This page can only be accessed via PUT only.", 400));
        }
    }

    protected void assertDeleteOnly()
    {
        if (!fc.getRequest().isDelete())
        {
            throw new Exception(new Error("This page can only be accessed via DELETE only.", 400));
        }
    }

    protected void assertHasErrors()
    {
        if (fc.getSession().has("error"))
        {
            InputError ie = (InputError) fc.getSession().get("error");
            fc.getSession().delete("error");
            throw new Exception(new Error(ie));
        }
    }

    protected HashMap<String, Object> input(String... names)
    {
        HashMap<String, Object> out = new HashMap<>();

        for (String name : names)
        {
            out.put(name, fc.getRequest().params().get(name));
        }

        return out;
    }

    protected void setEntityFields(Core.Entity.Abstract entity, Map<String, Object> fields) throws NoSuchFieldException, SQLException, IllegalAccessException
    {
        for (Map.Entry<String, Object> field : fields.entrySet())
        {
            Field f = entity.getClass().getDeclaredField(
                    field.getKey().endsWith("_id") ? field.getKey().substring(0, field.getKey().length() - 3) : field.getKey()
            );

            Object value = null;
            if (field.getValue() instanceof String || field.getValue() == null)
            {
                value = castSingleInputValueForEntity(f.getName(), f.getType(), (String) field.getValue());
            }
            else
            {
                throw new RuntimeException("Setting entity data via 'setEntityFields' only works with String data types.");
            }

            f.set(entity, value);
        }
    }

    private Object castSingleInputValueForEntity(String name, Class<?> cls, String value) throws IllegalArgumentException, SQLException
    {
        if (value == null || value.isEmpty())
        {
            return EntityManager.getNullValueForEntityField(cls);
        }

        if (cls.isPrimitive())
        {
            return castSinglePrimitiveInputValueForEntity(name, cls, value);
        }

        if (Number.class.isAssignableFrom(cls))
        {
            if (Integer.class.equals(cls))
            {
                return Integer.parseInt(value);
            }
            if (Float.class.equals(cls))
            {
                return Float.parseFloat(value);
            }
            if (Double.class.equals(cls))
            {
                return Double.parseDouble(value);
            }
            if (Long.class.equals(cls))
            {
                return Long.parseLong(value);
            }
            if (Byte.class.equals(cls))
            {
                return Byte.parseByte(value);
            }

            throw new IllegalArgumentException("Invalid numeric data type specified.");
        }

        if (String.class.equals(cls))
        {
            return value == null ? "" : value;
        }

        if (Enum.class.isAssignableFrom(cls))
        {
            return Enum.valueOf((Class<? extends Enum>) cls, value);
        }

        if (Core.Entity.Abstract.class.isAssignableFrom(cls))
        {
            DatabaseTable an = cls.getAnnotation(DatabaseTable.class);
            return EntityManager.getManagerInstance((Class<? extends EntityManager.Base>) an.daoClass()).queryForId(value);
        }

        if (Boolean.class.equals(cls))
        {
            return !(value.equals("off") || value.equals("false") || value.equals("no") || value.equals("0"));
        }

        if (Date.class.equals(cls))
        {
            try
            {
                return Core.App.getDateTimeFormat().parse(value);
            }
            catch (ParseException e)
            {
                try
                {
                    return Core.App.getDateFormat().parse(value);
                }
                catch (ParseException ignore) { }
            }

            return null;
        }

        throw new IllegalArgumentException("Invalid data type specified for `" + name + "`.");
    }

    private Object castSinglePrimitiveInputValueForEntity(String name, Class<?> cls, String value)
    {
        switch (cls.getTypeName())
        {
            case "int":
                return Integer.parseInt(value);

            case "float":
                return Float.parseFloat(value);

            case "double":
                return Double.parseDouble(value);

            case "long":
                return Long.parseLong(value);

            case "byte":
                return Byte.parseByte(value);

            case "boolean":
                return !(value.equals("off") || value.equals("false") || value.equals("no") || value.equals("0"));
        }

        throw new IllegalArgumentException("Invalid data type specified for `" + name + "`.");
    }

    protected Dao.CreateOrUpdateStatus saveEntity(Core.Entity.Abstract entity) throws SQLException
    {
        Object id = entity.extractId();
        if (id != null && entity.getDao().idExists(id))
        {
            return new Dao.CreateOrUpdateStatus(false, true, entity.update());
        }
        else
        {
            return new Dao.CreateOrUpdateStatus(true, false, entity.create());
        }
    }

    protected InputError error()
    {
        if (!fc.getSession().has("error"))
        {
            error = true;
            fc.getSession().set("error", new InputError());
        }

        return (InputError) fc.getSession().get("error");
    }

    protected InputError error(String key, String message)
    {
        InputError ie = error();
        ie.put(key, message);
        return ie;
    }
}
