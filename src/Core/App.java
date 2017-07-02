package Core;

import com.j256.ormlite.db.MysqlDatabaseType;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.flywaydb.core.Flyway;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class App
{
    final public static String ENCODING = "UTF-8";

    public static Args opt;

    public static void main(String args[]) throws Exception
    {
        Options options = new Options();

        options.addOption("p", "port", true, "The port number the web app will be accessible from.");

        CommandLine cmd = (new DefaultParser()).parse(options, args);
        opt = new Args(cmd);

        Flyway flyway = new Flyway();
        flyway.setDataSource(getDb());
        flyway.setLocations("filesystem:./db/migration");
        flyway.migrate();

        NanoHTTPD web = new Web.App();
        web.start(opt.timeout, false);
    }

    public static void log(String format, Object ...args)
    {
        System.out.printf(format + "\n", args);
    }

    public static void error(String format, Object ...args)
    {
        System.err.printf(format + "\n", args);
    }

    public static void debug(Exception e)
    {
        e.printStackTrace(System.err);
    }

    public static void dump(Object obj)
    {
        System.out.println(ReflectionToStringBuilder.toString(obj));
    }

    private static MysqlDataSource db;

    public static MysqlDataSource getDb()
    {
        if (db == null)
        {
            db = new MysqlDataSource();

            db.setPort(opt.dbport);
            db.setServerName(opt.dbhost);
            db.setDatabaseName(opt.dbname);

            db.setUser(opt.dbuser);
            db.setPassword(opt.dbpasswd);
        }

        return db;
    }

    private static ConnectionSource source;

    public static ConnectionSource getDbConnectionSource() throws SQLException
    {
        if (source == null)
        {
            source = new DataSourceConnectionSource(getDb(), new MysqlDatabaseType());
        }

        return source;
    }

    private static DateFormat dateTimeFormat;

    public static DateFormat getDateTimeFormat()
    {
        if (dateTimeFormat == null)
        {
            dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        return dateTimeFormat;
    }

    private static DateFormat dateFormat;

    public static DateFormat getDateFormat()
    {
        if (dateFormat == null)
        {
            dateFormat = new SimpleDateFormat("yyy-MM-dd");
        }

        return dateFormat;
    }

    public static byte[] getBytes(String str)
    {
        try
        {
            return str.getBytes(Core.App.ENCODING);
        }
        catch (UnsupportedEncodingException e)
        {
            return str.getBytes();
        }
    }

    public static class Args
    {
        public int port = 8080;
        public int timeout = 5000;

        public String dbhost = "localhost";
        public int dbport = 3306;
        public String dbname = "cse310";
        public String dbuser = "root";
        public String dbpasswd = "";

        public String salt = "\\%a#L)[j1t(5\\b%!Esb]|oguUykf\\>]kwW{QDd<Rri/`Yy<^SoTQ8apE-ca+A#Ed";

        private Args(CommandLine cmd)
        {
            if (cmd.hasOption("p"))
            {
                port = Integer.parseInt(cmd.getOptionValue("p"));
            }
        }
    }
}
