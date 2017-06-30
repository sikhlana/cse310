${container("title", "Products")}

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1 class="page-title">Products</h1>

            <div class="top-ctrl">
                <a class="button small primary" href="${adminlink('products/add')}">+ Add New Product</a>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12">
            <#list products>
                <ul id="item-list" class="products">
                    <#items as product>
                        <li>
                            ${adminlink('products/edit', product)}
                        </li>
                    </#items>
                </ul>
            <#else>
                <p id="empty-list">No products have been added yet.</p>
            </#list>
        </div>
    </div>
</div>