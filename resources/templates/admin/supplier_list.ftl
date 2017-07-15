${container("title", "Suppliers")}

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1 class="page-title">Suppliers</h1>

            <div class="top-ctrl">
                <a class="button small primary" href="${adminlink('suppliers/add')}">+ Add New Supplier</a>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12">
            <#list suppliers>
                <div class="list-header">
                    <h3>Suppliers</h3>
                </div>

                <ol id="supplier-list" class="item-list">
                    <#items as supplier>
                        <li>
                            <a href="${adminlink("suppliers/edit", supplier)}" class="anchor">
                                <h4 class="title">${supplier.name}</h4>
                            </a>

                            <a class="delete-item delete-button" href="${adminlink("suppliers/delete", supplier)}">
                                <i class="fa fa-times"></i>
                            </a>
                        </li>
                    </#items>
                </ol>

                <div class="list-footer">
                    Showing ${suppliers?size} of ${suppliers?size} items.
                </div>
            <#else>
                <p id="empty-list">No suppliers have been added yet.</p>
            </#list>
        </div>
    </div>
</div>