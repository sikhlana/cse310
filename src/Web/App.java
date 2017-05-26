package Web;

import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD
{
    public App()
    {
        super(Core.App.opt.port);
        Core.App.log("Listening to port %d...", Core.App.opt.port);
    }

    @Override
    public Response serve(IHTTPSession session)
    {
        Core.App.log("%s %s Connecting IP: %s", session.getMethod(), session.getUri(), session.getRemoteIpAddress());
        FrontController controller = new FrontController(session);
        return controller.run();
    }
}
