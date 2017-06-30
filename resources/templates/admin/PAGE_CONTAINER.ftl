<!DOCTYPE html>
<html lang="en_US" id="admin">
<head>
    <#include '../partials/head.ftl'>
</head>
<body id="<#if body??>${body}<#else>default</#if>">
    <header id="header">
        <div class="container">
            <div class="row">
                <div class="col-xs-12">
                    <nav id="main-navigation">
                        <ul>
                            <li>
                                <a class="navigation-link <#if section == 'dashboard'>selected</#if>" href="${link("admin")}">
                                    <span class="icon"><i class="fa fa-dashboard"></i></span>
                                    <p>Dashboard</p>
                                </a>
                            </li>

                            <li>
                                <a class="navigation-link <#if section == 'pos'>selected</#if>" href="${adminlink("pos")}">
                                    <span class="icon"><i class="fa fa-desktop"></i></span>
                                    <p>Point of Sale</p>
                                </a>
                            </li>

                            <li>
                                <a class="navigation-link <#if section == 'products'>selected</#if>" href="${adminlink("products")}">
                                    <span class="icon"><i class="fa fa-shopping-bag"></i></span>
                                    <p>Products</p>
                                </a>
                            </li>

                            <li>
                                <a class="navigation-link <#if section == 'rentals'>selected</#if>" href="${adminlink("rentals")}">
                                    <span class="icon"><i class="fa fa-paper-plane-o"></i></span>
                                    <p>Rentals</p>
                                </a>
                            </li>

                            <li>
                                <a class="navigation-link <#if section == 'suppliers'>selected</#if>" href="${adminlink("suppliers")}">
                                    <span class="icon"><i class="fa fa-truck"></i></span>
                                    <p>Suppliers</p>
                                </a>
                            </li>

                            <li>
                                <a class="navigation-link <#if section == 'users'>selected</#if>" href="${adminlink("users")}">
                                    <span class="icon"><i class="fa fa-users"></i></span>
                                    <p>Users</p>
                                </a>
                            </li>

                            <li>
                                <a class="navigation-link <#if section == 'reports'>selected</#if>" href="${adminlink("reports")}">
                                    <span class="icon"><i class="fa fa-file-text-o"></i></span>
                                    <p>Reports</p>
                                </a>
                            </li>
                        </ul>
                    </nav>

                    <nav id="visitor-navigation">
                        <ul>
                            <li>
                                <a class="navigation-link <#if section == 'account'>selected</#if>" href="${link("account")}">
                                    <span class="icon"><i class="fa fa-user-o"></i></span>
                                    <p>Account</p>
                                </a>
                            </li>

                            <li>
                                <a class="navigation-link" href="${link("logout")}">
                                    <span class="icon"><i class="fa fa-sign-out"></i></span>
                                    <p>Logout</p>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
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