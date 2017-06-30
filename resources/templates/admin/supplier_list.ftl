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

                <ul id="item-list" class="suppliers">
                    <#items as supplier>
                        <li id="supplier-${supplier['id']}" class="list-item ${supplier?item_parity}">
                            <a href="${adminlink("suppliers/edit", supplier)}"></a>
                        </li>
                    </#items>
                </ul>

                <div class="list-footer">
                    Showing ${suppliers?size} of ${suppliers?size} items.
                </div>
            <#else>
                <p id="empty-list">No suppliers have been added yet.</p>
            </#list>
        </div>
    </div>
</div>