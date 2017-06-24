package Web.Route;

import Core.ParameterBag;
import Web.Link;
import Web.Router;

import java.util.Map;

public class Administration implements MatchInterface, BuildInterface
{
    @Override
    public Router.Match match(String path, Router router, ParameterBag params)
    {
        return router.match(path, Routes.values(), params);
    }

    @Override
    public String build(String prefix, String action, Link builder, Map<String, Object> data, Map<String, Object> params)
    {
        return prefix + "/" + builder.build(action, Routes.values(), data, params);
    }

    enum Routes implements Router.Route
    {
        index(Web.Route.Admin.Home.class),
        pos(Web.Route.Admin.Pos.class),
        products(Web.Route.Admin.Products.class),
        suppliers(Web.Route.Admin.Suppliers.class),
        users(Web.Route.Admin.Users.class),
        rentals(Web.Route.Admin.Rentals.class),
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
}
