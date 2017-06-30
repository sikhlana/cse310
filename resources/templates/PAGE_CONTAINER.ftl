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
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12 text-right">
                    <p id="powered-by">Made with &hearts; by <i>Onegai Sensei</i>.</p>
                </div>
            </div>
        </div>
    </footer>
</body>
</html>