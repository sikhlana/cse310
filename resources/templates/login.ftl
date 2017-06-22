${container("containerTemplate", "PAGE_CONTAINER_SIMPLE")}
${container("title", "Login")}
${container("body", "login")}

<div class="wrapper">
    <h3>Login</h3>

    <form class="form login-form" action="${link("login/login")}" method="post">
        <#if error??>
            ${error}
        </#if>

        <div class="form-field">
            <label class="form-label" for="ctrl_email">Email</label>
            <input autofocus tabindex="1" type="email" name="email" id="ctrl_email" class="form-input" />
        </div>

        <div class="form-field">
            <label class="form-label" for="ctrl_password">Password</label>
            <input tabindex="2" type="password" name="password" id="ctrl_password" class="form-input" />
        </div>

        <button type="submit" class="button primary" tabindex="4">Login</button>
        <label class="remember-me"><input type="checkbox" name="remember" checked tabindex="3" /> Remember Me</label>

        <#if redirect??>
            <input type="hidden" name="redirect" value="${redirect}" />
        </#if>

        <input type="hidden" name="_token" value="${fc.getSession().getCsrfToken()}" />
    </form>

    <p id="powered-by">Made with &hearts; by <i>Onegai Sensei</i>.</p>
</div>