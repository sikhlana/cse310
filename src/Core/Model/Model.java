package Core.Model;

import org.apache.commons.dbutils.QueryRunner;

import java.text.ParseException;

abstract class Model
{
    private String primaryKey[] = {"id"};

    private boolean exists = false;

    public void save()
    {
        QueryRunner runner = new QueryRunner(Core.App.getDb());

        if (exists)
        {
            ;
        }
        else
        {
            ;
        }

        exists = true;
    }

    interface Field { }

    interface Relation { }

    protected enum Types
    {
        INT, FLOAT, STRING, BINARY, TEXT, BLOB, BOOLEAN, TIMESTAMP;

        public Object cast(String data)
        {
            switch (this)
            {
                case INT:
                    return Integer.parseInt(data);
                case FLOAT:
                    return Float.parseFloat(data);
                case BOOLEAN:
                    return Boolean.getBoolean(data);
                case TIMESTAMP:
                    try
                    {
                        return Core.App.getDateFormat().parse(data);
                    }
                    catch (ParseException e)
                    {
                        return null;
                    }
                default:
                    return data;
            }
        }

        public String toString(Object data)
        {
            switch (this)
            {
                case INT:
                    return String.format("%d", (Integer) data);
                case FLOAT:
                    return String.format("%f", (Float) data);
                case BOOLEAN:
                    return ((Boolean) data) ? "1": "0";
                case TIMESTAMP:
                    return Core.App.getDateFormat().format(data);
                default:
                    return (String) data;
            }
        }
    }
}
