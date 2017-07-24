App.ajaxForm = function(form) { this.init(form) };
App.ajaxForm.prototype =
{
    redirect: true,

    init: function(form)
    {
        this.form = form;
        this.form.on('submit', $.context(this, 'submit'));

        if (redirect = this.form.data('redirect'))
        {
            this.redirect = App.isTrue(redirect);
        }
    },

    submit: function(e)
    {
        var data;
        e.preventDefault();

        if (this.form.attr('enctype') === 'multipart/form-data')
        {
            data = new FormData;

            this.form.find('[name]').each(function(k, obj)
            {
                var t = $(obj);

                if (t.attr('type') !== 'file')
                {
                    data.append(t.attr('name'), t.val());
                }
                else
                {
                    for (var i = 0; i < t[0].files.length; i++)
                    {
                        data.append(t.attr('name'), t[0].files[i]);
                    }
                }
            });
        }
        else
        {
            data = this.form.serialize();
        }

        App.ajax(
            this.form.attr('action'), this.form.attr('method'),
            data, $.context(this, 'success')
        );

        this.form.find('[data-popped]').popover('destroy');
    },

    success: function(ajaxData, response)
    {
        var event = $.Event('AjaxFormSubmit');
        this.form.trigger(event, [ajaxData, response]);

        var modal = this.form.closest('.modal');
        if (modal.length > 0)
        {
            modal.modal('hide');
        }

        if (event.isDefaultPrevented())
        {
            return;
        }

        App.alert('success', 'Success', ajaxData.message || '', 2000);

        if (typeof ajaxData.redirect === 'string' && this.redirect)
        {
            window.setTimeout(function()
            {
                window.location.href = ajaxData.redirect;
            }, 1000);
        }
    }
};

App.register('form.ajax-form', 'App.ajaxForm');

App.deleteButton = function(button) { this.init(button); };
App.deleteButton.prototype =
{
    init: function(button)
    {
        this.button = button;
        this.button.on('click', $.context(this, 'process'));
    },

    process: function(e)
    {
        e.preventDefault();

        swal(
        {
            title: this.button.data('title') || "Are you sure?",
            text: this.button.data('message') || "You will not be able to recover this data!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, $.context(this, 'confirmed'));
    },

    confirmed: function()
    {
        App.ajax(this.button.data('href') || this.button.attr('href'), 'delete', {}, $.context(this, 'complete'));
    },

    complete: function(ajaxData)
    {
        App.alert('success', 'Deleted!', this.button.data('complete') || 'The data has been successfully deleted.', 2000);

        var listItem = this.button.closest('.item-list > li');
        if (listItem.length > 0)
        {
            if (listItem.eq(0).siblings().length < 1)
            {
                window.location.reload(true);
            }

            listItem.eq(0).remove();
        }

        var target = $(this.button.data('target'));
        if (target.length > 0)
        {
            target.remove();
        }
    }
};

App.register('a.delete-button', 'App.deleteButton');

App.listForm = function(form) { this.init(form); };
App.listForm.prototype =
{
    timeoutId: 0,

    init: function(form)
    {
        this.form = form;
        if (!form.hasClass('ajax-form'))
        {
            new App.ajaxForm(form);
        }

        this.form.find('.toggler > input').on('change', $.context(this, 'toggle'));
    },

    toggle: function(e)
    {
        if (this.timeoutId > 0)
        {
            clearTimeout(this.timeoutId);
        }

        var self = this;
        this.timeoutId = setTimeout(function()
        {
            self.form.submit();
        }, 1000);
    }
};

App.register('form.list-form', 'App.listForm');

App.toggleModal = function(button) { this.init(button) };
App.toggleModal.prototype =
{
    init: function(button)
    {
        this.button = button;
        this.modal = $(button.data('target'));

        this.button.on('click', $.context(this, 'click'));
    },

    click: function(e)
    {
        e.preventDefault();
        App.ajax(this.button.data('href') || this.button.attr('href'), 'get', {}, $.context(this, 'response'));
    },

    response: function(ajaxData)
    {
        var html = $(ajaxData.html);
        App.activate(html);

        this.modal.find('.modal-body').html(html);
        this.modal.modal('show');
    }
};

App.register('a.toggle-modal', 'App.toggleModal');

App.formInputItems = function(container) { this.init(container) };
App.formInputItems.prototype =
{
    timeoutId: 0,

    init: function(container)
    {
        this.container = container;

        var itemList = this.itemList = container.find('.input-item-list');
        var sentinel = itemList.find('> li.sentinel').removeClass('sentinel');

        this.sentinel = $("<p>").append(sentinel.clone()).html();
        sentinel.remove();

        this.itemList.find('> li').each(function()
        {
            new App.formInputItems.Item(itemList, $(this));
        });

        this.input = container.find('.input-item-adder').on('keypress', $.context(this, 'searchItem'));
        this.selectList = container.find('.item-search-list');

        this.input.val('');
    },

    searchItem: function(e)
    {
        if ((e.keyCode || e.which) === 13)
        {
            e.preventDefault();
            return;
        }

        if (this.timeoutId > 0)
        {
            clearTimeout(this.timeoutId);
        }

        var self = this;
        this.timeoutId = setTimeout(function()
        {
            var value = self.input.val();
            self.clearSelectList();

            App.ajax(
                self.container.data('href'), 'get', {q: value},
                $.context(self, 'processResponse')
            );
        }, 1000);
    },

    processResponse: function(ajaxData)
    {
        if (ajaxData.items.length < 1)
        {
            this.selectList.append('<li class="empty-result">No products found!</li>');
        }

        for (var i = 0; i < ajaxData.items.length; i++)
        {
            var item = ajaxData.items[i], html = $(this.sentinel);

            html.find('.input-item-title').html(item.title);
            html.find('.input-item-id').val(item.id);

            var snippets = html.find('.input-item-snippet > span');
            for (var j = 0; j < item.snippets.length; j++)
            {
                snippets.eq(j).html(item.snippets[j]);
            }

            new App.formInputItems.Item(this.itemList, html, this);
            this.selectList.append(html);
        }
    },

    clearSelectList: function()
    {
        this.selectList.html('');
    },

    clearInput: function()
    {
        this.input.val('');
    }
};

App.formInputItems.Item = function(list, item, cc) { this.init(list, item, cc) };
App.formInputItems.Item.prototype =
{
    init: function (list, item, cc)
    {
        this.list = list;
        this.item = item;
        this.cc = cc;

        this.item.find('.remove-input-item').on('click', $.context(this, 'deleteItem'));
        this.item.on('click', $.context(this, 'processClick'));

        if (typeof cc !== 'undefined')
        {
            this.item.find('.input-item-id').prop('disabled', true);
        }
    },

    deleteItem: function(e)
    {
        e.preventDefault();
        this.item.remove();
    },

    processClick: function(e)
    {
        if (this.item.parent().hasClass('item-search-list'))
        {
            e.preventDefault();

            var newItem = this.item.clone();

            newItem.find('.input-item-id').prop('disabled', false);
            new App.formInputItems.Item(this.list, newItem);

            this.list.append(newItem);
            this.item.remove();

            this.cc.clearInput();
        }
    }
};

App.register('.form-input-items', 'App.formInputItems');

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

App.register(".field-copy", "App.fieldCopy");