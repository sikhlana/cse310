package Core;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
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

        options.addOption("nw", "no-web", false, "Disables the web application.")
               .addOption("np", "no-pos", false, "Disables the point-of-sale application.")
               .addOption("p", "port", true, "The port number the web app will be accessible from.")
               .addOption("m", "migrate", false, "Runs the migration module.");

        CommandLine cmd = (new DefaultParser()).parse(options, args);
        opt = new Args(cmd);

        if (cmd.hasOption("m"))
        {
            Flyway flyway = new Flyway();
            flyway.setDataSource(getDb());
            flyway.setLocations("filesystem:./db/migration");
            flyway.migrate();

            return;
        }

        if (opt.web)
        {
            NanoHTTPD web = new Web.App();
            web.start(opt.timeout, false);
        }

        if (opt.pos)
        {
            Thread pos = new Pos.App();
            pos.start();
        }
    }

    public static void log(String format, Object ...args)
    {
        System.out.printf(format + "\n", args);
    }

    public static void error(String format, Object ...args)
    {
        System.err.printf(format + "\n", args);
    }

    public static void dump(Object obj)
    {
        System.out.println(ReflectionToStringBuilder.toString(obj));
    }

    private static MysqlDataSource db;

    public static DataSource getDb()
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

    public static ConnectionSource getDbConnectionSource()
    {
        if (source == null || !source.isOpen(null))
        {
            try
            {
                source = new JdbcConnectionSource(String.format(
                        "jdbc:mysql://%s:%d/%s", opt.dbhost, opt.dbport, opt.dbname
                ), opt.dbuser, opt.dbpasswd);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }

        return source;
    }

    private static DateFormat dateFormat;

    public static DateFormat getDateFormat()
    {
        if (dateFormat == null)
        {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        return dateFormat;
    }

    public static class Args
    {
        public int port = 8080;
        public int timeout = 5000;

        public boolean web = true;
        public boolean pos = true;

        public String dbhost = "localhost";
        public int dbport = 3306;
        public String dbname = "cse310";
        public String dbuser = "root";
        public String dbpasswd = "";

        public String salt = "\\%a#L)[j1t(5\\b%!Esb]|oguUykf\\>]kwW{QDd<Rri/`Yy<^SoTQ8apE-ca+A#Ed";

        private Args(CommandLine cmd)
        {
            web = !cmd.hasOption("nw");
            pos = !cmd.hasOption("np");

            if (cmd.hasOption("p"))
            {
                port = Integer.parseInt(cmd.getOptionValue("p"));
            }
        }
    }
}
