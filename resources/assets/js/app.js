
/**
 * First we will load all of this project's JavaScript dependencies which
 * includes Vue and other libraries. It is a great starting point when
 * building robust, powerful web applications using Vue and Laravel.
 */

require('./bootstrap');

/* --- */

$.context = function(fn, context, extra)
{
    if (typeof context === 'string')
    {
        var _context = fn;
        fn = fn[context];
        context = _context;
    }

    return function()
    {
        if (typeof extra === 'object')
        {
            for (var i = extra.length - 1; i >= 0; i--)
            {
                Array.prototype.unshift.call(arguments, extra[i]);
            }
        }

        return fn.apply(context, arguments);
    };
};

window.App =
{
    init: function()
    {
        App.activate(document);

        axios.interceptors.request.use(function(config)
        {
            NProgress.start();
            return config;
        });

        axios.interceptors.response.use(function(response)
        {
            NProgress.done();
            return response;
        }, function(error)
        {
            NProgress.done();
            return Promise.reject(error);
        });
    },

    isTrue: function(val)
    {
        return val === true || val === 1 || val === '1' || val === 'on' || val === 'yes' || val === 'true' || val === 'si';
    },

    activate: function(element)
    {
        var $element = $(element);

        $element.trigger('Activate').removeClass('__Activator');
        $element.find('noscript').empty().remove();

        $(document).trigger(
        {
            element: element,
            type: 'ActivateHtml'
        });

        return element;
    },

    register: function(selector, fn, event)
    {
        if (typeof fn === 'string')
        {
            var className = fn;
            fn = function(i) { App.create(className, this); };
        }

        $(document).bind(event || 'ActivateHtml', function(e)
        {
            $(e.element).find(selector).each(fn);
        });
    },

    create: function(className, element)
    {
        var $element = $(element),
            appObj = window,
            parts = className.split('.'), i;

        for (i = 0; i < parts.length; i++) { appObj = appObj[parts[i]]; }

        if (typeof appObj !== 'function')
        {
            return console.error('%s is not a function.', className);
        }

        if (!$element.data(className))
        {
            $element.data(className, new appObj($element));
        }

        return $element.data(className);
    },

    ajax: function(url, method, data, complete, config)
    {
        if (config !== 'object')
        {
            config = {};
        }

        var query = '';
        if (method.toLowerCase() === 'get')
        {
            $.each(data, function(key, value)
            {
                query += encodeURIComponent(key) + '=' + encodeURIComponent(value) + '&';
            });

            query = (url.includes('?') ? '&' : '?') + query.slice(0, -1);
            data = {};
        }

        $.extend(config,
        {
            url: url + query,
            method: method,
            data: data
        });

        axios.request(config).then(function(response)
        {
            complete(response.data, response);
        }).catch(function(error)
        {
            if (typeof error.response === 'object')
            {
                if (typeof error.response.data.error === 'string')
                {
                    App.alert('error', 'Error', error.response.error);
                }
                else if (typeof error.response.data.error === 'object')
                {
                    App.alert('error', 'Error', 'There are error(s) in your request.');

                    $.each(error.response.data.error, function(key, msg)
                    {
                        $('[name=' + key + ']').eq(0).popover(
                        {
                            content: msg,
                            placement: 'right',
                            trigger: 'manual'
                        }).popover('show').on('focus', function()
                        {
                            $(this).off('focus').popover('destroy');
                        }).attr('data-popped', 'true');
                    });

                    console.log(error);
                }
            }
            else
            {
                App.alert('error', 'Error', 'An undefined error occurred. Please check console for more information.')
                console.error(error);
            }
        });
    },

    alert: function(type, title, message, timer)
    {
        swal({
            title: title,
            text: message || '',
            type: type,
            allowOutsideClick: true,
            timer: timer || null
        });
    }
};

/**
 * Include all the necessary components.
 */

require('./components/form');
require('./components/image-uploader');
require('./components/sortable');
require('./components/register');
require('./components/pos');

/**
 * Now initialize the application.
 */

$(document).ready(function()
{
    App.init();
});