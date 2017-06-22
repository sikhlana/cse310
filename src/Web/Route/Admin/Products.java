package Web.Route.Admin;

import Core.ParameterBag;
import Web.Controller.Admin.Product;
import Web.Link;
import Web.Route.BuildInterface;
import Web.Route.MatchInterface;
import Web.Router;

import java.util.Map;

public class Products implements MatchInterface, BuildInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params)
    {
        String action = router.resolveActionWithIntegerParam(path, params, "id");
        return new Router.Match(Product.class, action, "products");
    }

    @Override
    public String build(String prefix, String action, Link builder, Map<String, Object> data, Map<String, Object> params)
    {
        return builder.buildLinkWithIntegerParam(prefix, action, data, "id", "title");
    }
}
