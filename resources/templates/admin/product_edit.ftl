<#if product['id'] == 0>
    <#assign title="Add New Product">
<#else>
    <#assign title="Edit Product: " + product['title']>
</#if>

${container("title", title)}

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1 class="page-title">${title}</h1>
        </div>
    </div>

    <div class="row">
        <form class="form product-form" action="${adminlink('products/save')}" method="post">
            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_title">Title</label>
                <input type="text" name="title" id="ctrl_title" class="form-input" value="${product['title']}" />
            </div>

            <div class="form-field col-xs-6">
                <label class="form-label" for="ctrl_type">Product Type</label>
                <select id="ctrl_type" name="type" class="form-input">
                    <#list productTypes as productType>
                        <option value="${productType.name()}" <#if productType.name() == product['type']>selected</#if>>${productType.title()}</option>
                    </#list>
                </select>
            </div>

            <div class="form-field col-xs-6">
                <label class="form-label" for="ctrl_rental_tier">Rental Tier</label>
                <select id="ctrl_rental_tier" name="rental_tier" class="form-input">
                <#list rentalTiers as rentalTier>
                    <option value="${rentalTier.value()}" <#if rentalTier.value() == product['rental_tier']>selected</#if>>${rentalTier.label()}</option>
                </#list>
                </select>
            </div>

            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_sku">Product SKU</label>
                <input type="text" name="sku" id="ctrl_sku" class="form-input" value="${product['sku']}" />
            </div>

            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_price">Price</label>
                <input type="text" name="price" id="ctrl_price" class="form-input" value="${product['price']}" />
            </div>

            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_description">Description</label>
                <textarea name="description" id="ctrl_description" class="form-input">${product['description']}</textarea>
            </div>

            <div class="form-field col-xs-6">
                <label class="form-label" for="ctrl_minimum_stock">Minimum Stock</label>
                <input type="number" name="minimum_stock" id="ctrl_minimum_stock" class="form-input" value="${product['minimum_stock']}" />
            </div>

            <div class="form-field col-xs-6">
                <label class="form-label" for="ctrl_stock">Current Stock</label>
                <input type="number" name="stock" id="ctrl_stock" class="form-input" value="${product['stock']}" />
            </div>

            <button type="submit" class="button primary">Save</button>

            <input type="hidden" name="_token" value="${fc.getSession().getCsrfToken()}" />
        </form>
    </div>
</div>