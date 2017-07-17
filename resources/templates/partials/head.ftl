<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="csrf-token" content="${fc.getSession().getCsrfToken()}" />

<title><#if title??>${title} | </#if>Khela Hobe!</title>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,400i,700|Lato:300,400,700|Montserrat:300,400,600" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootswatch/3.3.7/flatly/bootstrap.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"  />
<link rel="stylesheet" href="/static/css/libs.css" />
<link rel="stylesheet" href="/static/css/app.css" />

<script src="/static/js/app.js"></script>

<#if includedJs??>
    <#list includedJs as item>
        <script src="${item}"></script>
    </#list>
</#if>