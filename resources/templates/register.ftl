${container("containerTemplate", "PAGE_CONTAINER_SIMPLE")}
${container("title", "Register")}
${container("body", "login")}

<div class="wrapper">
    <h3>Register</h3>

    <form class="form login-form ajax-form" action="${link("register/register")}" method="post">
    <#if error??>
    ${error}
    </#if>

        <div class="form-field">
            <label class="form-label" for="ctrl_name">Full Name</label>
            <input autofocus tabindex="1" type="text" name="name" id="ctrl_name" class="form-input" />
        </div>

        <div class="form-field">
            <label class="form-label" for="ctrl_phone_number">Phone Number</label>
            <input tabindex="2" type="tel" name="phone_number" id="ctrl_phone_number" class="form-input" />
        </div>

        <div class="form-field">
            <label class="form-label" for="ctrl_email">Email</label>
            <input tabindex="3" type="email" name="email" id="ctrl_email" class="form-input" />
        </div>

        <div class="form-field">
            <label class="form-label" for="ctrl_password">Password</label>
            <input tabindex="4" type="password" name="password" id="ctrl_password" class="form-input password-input" />
            <div class="password-meter"><div class="strength"></div></div>
        </div>

        <div class="form-field">
            <label class="form-label" for="ctrl_password_confirm">Confirm Password</label>
            <input tabindex="5" type="password" name="password_confirm" id="ctrl_password_confirm" class="form-input" />
        </div>

        <button type="submit" class="button primary" tabindex="4">Register</button>

        <a class="button secondary" href="${link("login")}">Already have an account? Login!</a>

        <#if redirect??>
            <input type="hidden" name="redirect" value="${redirect}" />
        </#if>

        <input type="hidden" name="_token" value="${fc.getSession().getCsrfToken()}" />
    </form>

    <p id="powered-by">Made with &hearts; by <i>Onegai Sensei</i>.</p>
</div>