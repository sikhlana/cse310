package Web.Route;

import Core.ParameterBag;
import Web.Controller.Product;
import Web.Router;

public class Products implements MatchInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params)
    {
        String action = router.resolveActionWithIntegerParam(path, params, "product_id", "view");
        return new Router.Match(Product.class, action, "products");
    }
}
