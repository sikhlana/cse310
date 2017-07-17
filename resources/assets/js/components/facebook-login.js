App.facebookLogin = function(target) { this.init(target); };
App.facebookLogin.prototype =
{
    scope: [
        'email', 'public_profile', 'user_friends'
    ],

    options: {
        return_scopes: true
    },

    init: function(target)
    {
        this.target = target;
        this.form = $('#facebook-login-form');
        this.options.scope = this.scope.join(',');
        this.target.on('click', $.context(this, 'process'));
    },

    process: function(e)
    {
        e.preventDefault();
        FB.login($.context(this, 'processApi'), this.options);
    },

    processApi: function(response)
    {
        if (response.status !== 'connected')
        {
            this.options.auth_type = 'rerequest';
            Api.alert('error', 'Error', 'Unable to connect to Facebook API server. Please try again later.');
            return;
        }

        var auth = response.authResponse;
        if (auth.grantedScopes.split(',').length !== this.scope.length)
        {
            this.options.auth_type = 'rerequest';
            Api.alert('error', 'Error', 'Not all permissions were granted. Please try again.');
            return;
        }

        this.form.find('input[name=profile_id]').val(auth.userID);
        this.form.find('input[name=access_token]').val(auth.accessToken);
        this.form.submit();
    }
};

App.register('#facebook-login', 'App.facebookLogin');