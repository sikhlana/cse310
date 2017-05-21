package Core;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class App
{
    final public static String ENCODING = "UTF-8";

    public static Args opt;

    public static void main(String args[]) throws Exception
    {
        Options options = new Options();

        options.addOption("nw", "no-web", false, "Disables the web application.")
               .addOption("np", "no-pos", false, "Disables the point-of-sale application.")
               .addOption("p", "port", true, "The port number the web app will be accessible from.");

        opt = new Args((new DefaultParser()).parse(options, args));

        if (opt.web)
        {
            Thread web = new Web.App();
            web.start();
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

    public static class Args
    {
        public int port = 8080;

        public boolean web = true;
        public boolean pos = true;

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
