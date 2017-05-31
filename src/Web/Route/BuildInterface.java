package Web.Route;

import Web.Link;

import java.util.Map;

public interface BuildInterface
{
    String build(String prefix, String action, Link builder, Map<String, Object> data, Map<String, Object> params);
}
