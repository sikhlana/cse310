/** @param {jQuery} $ jQuery Object */
!function($, window, document, _undefined)
{
    $.context = function(fn, context)
    {
        if (typeof context === 'string')
        {
            var _context = fn;
            fn = fn[context];
            context = _context;
        }

        return function() { return fn.apply(context, arguments); };
    };

    var App =
    {
        init: function()
        {
            App.activate(document);
        },

        activate: function(element)
        {
            var $element = $(element);

            $element.trigger('Activate').removeClass('__Activator');
            $element.find('noscript').empty().remove();

            $(document)
                .trigger({ element: element, type: 'ActivateHtml' })
                .trigger({ element: element, type: 'ActivatePopups' })
                .trigger({ element: element, type: 'ActivationComplete' });

            var $form = $element.find('form.AutoSubmit:first');
            if ($form.length)
            {
                $(document).trigger('PseudoAjaxStart');
                $form.submit();
                $form.find('input[type="submit"], input[type="reset"]').hide();
            }

            return element;
        },

        register: function(selector, fn, event)
        {
            if (typeof fn === 'string')
            {
                var className = fn;
                fn = function(i)
                {
                    App.create(className, this);
                };
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

        ajax: function(url, params, complete)
        {
            $.ajax({
                url: url,
                beforeSend: function()
                {
                    NProgress.start();
                },
                cache: false,
                success: function(data)
                {
                    complete(data);
                },
                data: params,
                method: 'POST',
                dataType: 'json',
                error: function(error)
                {
                    if (typeof error.responseJSON.error === 'string')
                    {
                        swal(
                        {
                            title: "Error",
                            text: error.responseJSON.error,
                            type: "error",
                            allowOutsideClick: true
                        });
                    }
                    else if (typeof error.responseJSON.error === 'object')
                    {
                        swal(
                        {
                            title: "Error",
                            text: "There are error(s) in your request.",
                            type: "error",
                            allowOutsideClick: true
                        });

                        $.each(error.responseJSON.error, function (key, msg)
                        {
                            $('#ctrl_' + key).popover(
                            {
                                content: msg,
                                placement: 'right',
                                trigger: 'manual'
                            }).popover('show').on('focus', function()
                            {
                                $(this).off('focus').popover('destroy');
                            });
                        });
                    }
                },
                complete: function()
                {
                    NProgress.done();
                }
            });
        }
    };

    App.fieldCopy = function(container) { this.init(container) };
    App.fieldCopy.prototype =
    {
        init: function(container)
        {
            this.container = container;
            this.html = container.html();
            container.html('');

            this.current = $(this.html);
            this.append();
            this.current.data('added', false);
        },

        append: function()
        {
            var self = this;

            this.current.find("input").eq(0).on('focus', function()
            {
                $(this).off('focus');
                self.append();
            });

            this.container.append(this.current);
            this.current = $(this.html);
        }
    };

    App.ajaxForm = function(form) { this.init(form) };
    App.ajaxForm.prototype =
    {
        init: function(form)
        {
            this.form = form;
            form.on('submit', $.context(this, 'submit'));
        },

        submit: function(e)
        {
            var self = this;
            e.preventDefault();

            App.ajax(self.form.attr('action'), self.form.serialize(), function(ajaxData)
            {
                swal(
                {
                    title: "Success!",
                    type: 'success',
                    allowOutsideClick: true,
                    timer: 2000
                });

                console.log(ajaxData);

                if (typeof ajaxData.redirect === 'string')
                {
                    window.setTimeout(function()
                    {
                        window.location.href = ajaxData.redirect;
                    }, 1000);
                }
            });
        }
    };

    App.register(".field-copy", "App.fieldCopy");
    App.register('.ajax-form', 'App.ajaxForm');

    window.App = App;

    $(document).ready(function()
    {
        App.init();
    });
}(jQuery, window, document);