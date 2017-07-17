App.sortableContainer = function(container) { this.init(container) };
App.sortableContainer.prototype =
{
    init: function(container)
    {
        this.container = container;
        this.container.sortable();
    }
};

App.register('.sortable-container', 'App.sortableContainer');