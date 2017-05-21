package Web;

import java.net.Socket;
import java.util.Date;

public class Controller implements Runnable
{
    private Socket socket;
    private Date time;

    private Request request;
    private Response response;

    protected Controller(Socket socket)
    {
        this.socket = socket;
        this.time = new Date();
    }

    public void run()
    {
        try
        {
            try
            {
                request = new Request(socket.getInputStream());
                request.parseRemoteAddress(socket.getRemoteSocketAddress());

                Core.App.log(
                        "%s /%s Connecting IP: %s",
                        request.getMethod(),
                        request.getPath(),
                        request.getClientIp()
                );
            }
            catch (Request.RequestException e)
            {

            }

            response = new Response(socket.getOutputStream());
            response.send(time.toString());
            socket.close();
        }
        catch (Exception e)
        {
            Core.App.error("Web/Controller Exception: %s", e);
        }
    }

    public Socket getSocket()
    {
        return socket;
    }

    public Date getTime()
    {
        return time;
    }

    public Request getRequest()
    {
        return request;
    }

    public Response getResponse()
    {
        return response;
    }
}
