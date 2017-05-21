package Web;

import java.net.ServerSocket;
import java.net.Socket;

public class App extends Thread
{
    public void run()
    {
        try
        {
            ServerSocket server = new ServerSocket(Core.App.opt.port);
            Core.App.log("Listening to port %d...", Core.App.opt.port);

            //noinspection InfiniteLoopStatement
            while (true)
            {
                try (Socket socket = server.accept())
                {
                    (new Thread(new Controller(socket))).run();
                }
            }
        }
        catch (Exception e)
        {
            Core.App.error("Thread/Web Exception: %s", e);
        }
    }
}
