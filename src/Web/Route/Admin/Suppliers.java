package Web.Route.Admin;

import Core.ParameterBag;
import Web.Controller.Admin.Supplier;
import Web.Link;
import Web.Route.BuildInterface;
import Web.Route.MatchInterface;
import Web.Router;

import java.util.Map;

public class Suppliers implements MatchInterface, BuildInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params)
    {
        String action = router.resolveActionWithIntegerParam(path, params, "supplier_id");
        return new Router.Match(Supplier.class, action, "suppliers");
    }

    @Override
    public String build(String prefix, String action, Link builder, Map<String, Object> data, Map<String, Object> params)
    {
        return builder.buildLinkWithIntegerParam(prefix, action, data, "id", "name");
    }
}
