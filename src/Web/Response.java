package Web;

import java.io.IOException;
import java.io.OutputStream;

public class Response
{
    private OutputStream stream;

    Response(OutputStream stream)
    {
        this.stream = stream;
    }

    public void send(String resp) throws IOException
    {
        stream.write(("HTTP/1.1 200 OK\r\n\r\n" + resp).getBytes("UTF-8"));
    }
}
