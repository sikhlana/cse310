App.PageBuilder = function(container) { this.init(container); };
App.PageBuilder.prototype =
{
    init: function(container)
    {
        this.container = container;
        this.container.find('.container-adder').on('click', $.context(this, 'addContainer'));

        this.containerAddForm = $('#container-add-form');
        this.containerAddForm.find('.ajax-form').on('AjaxFormSubmit', $.context(this, 'addContainerProcess'));
    },

    addContainer: function()
    {
        this.containerAddForm.modal('show');
    },

    addContainerProcess: function(e, ajaxData)
    {
        e.preventDefault();

        var html = $(ajaxData.html);
        App.activate(html);

        this.container.find('.containers').append(html);
    }
};

App.register('#page-builder', 'App.PageBuilder');

App.PageBuilder.BlockContainer = function(block) { this.init(block); };
App.PageBuilder.BlockContainer.prototype =
{
    init: function(block)
    {
        this.block = block;
        console.log(this.block);
    }
};

App.register('.page-builder-blocks', 'App.PageBuilder.BlockContainer');