App.Pos = {
    processAjaxResponse: function(data)
    {
        try
        {
            console.log(data);
            if (data.params.total)
            {
                $('.cart-price').html(data.params.total);
            }
        }
        catch (e) { console.error(e); }
    }
};

App.Pos.SkuSearchForm = function(form) { this.init(form); };
App.Pos.SkuSearchForm.prototype =
{
    init: function(form)
    {
        this.form = form;
        this.field = this.form.find('input[type=search]').eq(0);

        this.field.on('keypress', $.context(this, 'input'));
        this.form.on('AjaxFormSubmit', $.context(this, 'submit'));
    },

    input: function(e)
    {
        if (this.field.val().length >= 6)
        {
            this.form.submit();
        }
    },

    submit: function(e, data)
    {
        e.preventDefault();
        $('#pos-item-list').data('item-list').push(data);
        App.Pos.processAjaxResponse(data);
    }
};

App.register('.sku-search-form', 'App.Pos.SkuSearchForm');

App.Pos.ItemList = function(container) { this.init(container); };
App.Pos.ItemList.prototype =
{
    init: function(container)
    {
        this.container = container;
        this.container.data('item-list', this);
    },

    push: function(data)
    {
        console.log(data);

        var item = $('#item-' + data.params.product_id);
        if (item.length < 1)
        {
            item = $(data.html);
            this.container.append(item);
            App.activate(item);
        }

        item.find('input[name=quantity]').val(data.params.product_qty);
    }
};

App.register('#pos-item-list', 'App.Pos.ItemList');

App.Pos.Item = function(item) { this.init(item); };
App.Pos.Item.prototype =
{
    init: function(item)
    {
        this.item = item;

        this.item.find('.qty-form').on('AjaxFormSubmit', $.context(this, 'submitQtyForm'));
    },

    submitQtyForm: function(e, data)
    {
        e.preventDefault();
        App.Pos.processAjaxResponse(data);

        if (data.params.removed)
        {
            var listItem = this.button.closest('.item-list > li');
            if (listItem.length > 0)
            {
                listItem.eq(0).remove();
            }
        }
    }
};

App.register('#pos-item-list > li', 'App.Pos.Item');