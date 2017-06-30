package Web.Route.Admin;

import Core.ParameterBag;
import Web.Controller.Admin.Product.Image;
import Web.Controller.Admin.Product.Product;
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
        String action = router.resolveActionWithIntegerParam(path, params, "product_id");
        System.out.println(action);
        return router.match(action, Routes.class, params);
    }

    @Override
    public String build(String prefix, String action, Link builder, Map<String, Object> data, Map<String, Object> params)
    {
        prefix = builder.buildLinkWithIntegerParam(
                prefix, "",
                data.containsKey("product") ? (Map) data.get("product") : data,
                "id", "title"
        );

        return prefix + "/" + builder.build(action, Routes.class, data, params);
    }

    enum Routes implements Router.Route
    {
        defaultRoute(Default.class),
        images(Images.class),
        ;

        final public Class<? extends MatchInterface> route;

        Routes(Class<? extends MatchInterface> route)
        {
            this.route = route;
        }

        @Override
        public Class<? extends MatchInterface> getRoute()
        {
            return route;
        }
    }

    public static class Default implements MatchInterface
    {
        @Override
        public Router.Match match(String path, Router router, ParameterBag params)
        {
            return new Router.Match(Product.class, path, "products");
        }
    }

    public static class Images implements MatchInterface, BuildInterface
    {
        @Override
        public Router.Match match(String path, Router router, ParameterBag params)
        {
            String action = router.resolveActionWithIntegerParam(path, params, "image_id");
            return new Router.Match(Image.class, action, "products");
        }

        @Override
        public String build(String prefix, String action, Link builder, Map<String, Object> data, Map<String, Object> params)
        {
            return builder.buildLinkWithIntegerParam(prefix, action, data, "id");
        }
    }
}
