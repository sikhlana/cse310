package Web.Route.Admin;

import Core.ParameterBag;
import Web.Controller.Admin.User;
import Web.Link;
import Web.Route.BuildInterface;
import Web.Route.MatchInterface;
import Web.Router;

import java.util.Map;

public class Users implements MatchInterface, BuildInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params)
    {
        String action = router.resolveActionWithIntegerParam(path, params, "user_id");
        return new Router.Match(User.class, action, "users");
    }

    @Override
    public String build(String prefix, String action, Link builder, Map<String, Object> data, Map<String, Object> params)
    {
        return builder.buildLinkWithIntegerParam(prefix, action, data, "user_id", "name");
    }
}
