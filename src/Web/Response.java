package Web;

import Web.ViewRenderer.AbstractRenderer;
import fi.iki.elonen.NanoHTTPD;

import java.io.ByteArrayInputStream;

public class Response extends NanoHTTPD.Response
{
    Response(AbstractRenderer renderer)
    {
        super(renderer.getResponseStatus(), renderer.getMimeType(), new ByteArrayInputStream(renderer.render().bytes), renderer.render().length);
    }
}
