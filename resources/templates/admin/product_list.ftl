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
            <#if products??>
                <ul id="item-list" class="products">
                    <#list products as product>

                    </#list>
                </ul>
            <#else>
                <p id="empty-list">No products have been added yet.</p>
            </#if>
        </div>
    </div>
</div>