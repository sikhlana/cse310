App.passwordStrength = function($input)
{
    var meter = $('.password-meter > .strength'),
        strengthCheck = function()
        {
            var result = zxcvbn($input.val()),
                color = '#BF3100';

            switch (result['score'])
            {
                case 4:
                    color = '#B4D304';
                    break;

                case 3:
                    color = '#F5BB00';
                    break;

                case 2:
                    color = '#FF4E00';
            }

            meter.css(
            {
                background: color,
                width: (result['score'] / 4) * 100 + '%'
            });
        };

    window.setInterval(strengthCheck, 200);
};

App.register(".password-input", "App.passwordStrength");