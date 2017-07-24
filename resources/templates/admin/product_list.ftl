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
                <div class="list-header">
                    <h3>Products</h3>
                </div>

                <ol id="product-list" class="item-list">
                    <#items as product>
                        <li>
                            <a href="${adminlink("products/edit", product)}" class="anchor">
                                <h4 class="title">${product.title}</h4>
                            </a>

                            <a class="delete-item delete-button" href="${adminlink("products/delete", product)}">
                                <i class="fa fa-times"></i>
                            </a>
                        </li>
                    </#items>
                </ol>

                <div class="list-footer">
                    Showing ${products?size} of ${products?size} items.
                </div>
            <#else>
                <p id="empty-list">No products have been added yet.</p>
            </#list>
        </div>
    </div>
</div>