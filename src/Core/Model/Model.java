package Core.Model;

import org.apache.commons.dbutils.QueryRunner;

import java.util.Collection;
import java.util.LinkedHashSet;

abstract class Model
{
    private String primaryKey = "id";
    private Collection<Field> fields = new LinkedHashSet<>();

    private boolean exists = false;

    public void save()
    {
        QueryRunner runner = new QueryRunner(Core.App.getDb());
    }

    private static void setFields()
    {
        throw new RuntimeException("This method needs to be overwritten by child classes.");
    }

    private class Field
    {
        final public static int TYPE_INT        = 1;
        final public static int TYPE_FLOAT      = 2;
        final public static int TYPE_CHAR       = 3;
        final public static int TYPE_BINARY     = 4;
        final public static int TYPE_TEXT       = 5;
        final public static int TYPE_BLOB       = 6;

        final String name;
        final int type;

        public Field(String name, int type)
        {
            this.name = name;
            this.type = type;
        }
    }
}
