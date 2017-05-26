package Core.Model;

import java.util.Date;

public class User extends Model
{
    enum Fields implements Field
    {
        id(Types.INT, "increments"),
        name(Types.STRING),
        email(Types.STRING, "unique"),
        password(Types.BINARY, "encrypt"),
        is_staff(Types.BOOLEAN, "", false),
        remember_token(Types.BINARY),
        created_at(Types.TIMESTAMP),
        updated_at(Types.TIMESTAMP);

        private Types type;
        private String defaultValue = "";

        public boolean increments = false;
        public boolean unique = true;

        Fields(Types type)
        {
            this.type = type;
        }

        Fields(Types type, String props)
        {
            this(type);

            for (String prop : props.split("|"))
            {
                switch (prop)
                {
                    case "increments":
                    case "unique":
                    case "encrypt":
                        try
                        {
                            getClass().getDeclaredField(prop).setBoolean(this, true);
                        }
                        catch (Exception e)
                        {
                            Core.App.error(e.toString());
                        }
                        continue;
                }


            }
        }

        Fields(Types type, String props, Object defaultValue)
        {
            this(type, props);
            this.defaultValue = type.toString(defaultValue);
        }

        public int getPosition()
        {
            return ordinal() + 1;
        }

        public Types getType()
        {
            return type;
        }

        public String getName()
        {
            return name();
        }
    }

    enum Relations implements Relation
    {

    }
}
