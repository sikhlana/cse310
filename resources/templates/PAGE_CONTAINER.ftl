<!DOCTYPE html>
<html lang="en_US" id="public">
<head>
    <#include 'partials/head.ftl'>
</head>
<body id="<#if body??>${body}<#else>default</#if>">
    <header id="header">

    </header>

    <section id="main-section">
        ${contents}
    </section>

    <footer id="footer">

    </footer>
</body>
</html>