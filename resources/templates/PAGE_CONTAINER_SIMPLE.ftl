<!DOCTYPE html>
<html lang="en_US" id="<#if html??>${html}<#else>public</#if>">
<head>
    <#include "partials/head.ftl">
</head>
<body id="<#if body??>${body}<#else>default</#if>">
    ${contents}
</body>
</html>