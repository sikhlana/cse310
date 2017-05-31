package Web.Route;

import Core.ParameterBag;
import Web.Controller.Product;
import Web.Link;
import Web.Router;

import java.util.Map;

public class Products implements MatchInterface, BuildInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params)
    {
        String action = router.resolveActionWithIntegerParam(path, params, "product_id", "view");
        return new Router.Match(Product.class, action, "products");
    }

    @Override
    public String build(String prefix, String action, Link builder, Map<String, Object> data, Map<String, Object> params)
    {
        return builder.buildLinkWithIntegerParam(prefix, action, data, "id", "title");
    }
}
