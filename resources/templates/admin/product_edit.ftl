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
        <form class="form product-form large" action="${adminlink('products/save')}" method="post">
            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_title">Title</label>
                <input type="text" name="title" id="ctrl_title" class="form-input" value="${product['title']}" />
            </div>

            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_description">Description</label>
                <textarea name="description" id="ctrl_description" class="form-input">${product['description']}</textarea>
            </div>

            <div class="form-field col-xs-12 col-sm-6">
                <label class="form-label" for="ctrl_type">Product Type</label>
                <select id="ctrl_type" name="type" class="form-input">
                    <#list productTypes as productType>
                        <option value="${productType.name()}" <#if productType == product['type']>selected</#if>>${productType.label()}</option>
                    </#list>
                </select>
            </div>

            <div class="form-field col-xs-12 col-sm-6">
                <label class="form-label" for="ctrl_sku">Product SKU</label>
                <input type="text" name="sku" id="ctrl_sku" class="form-input" value="${product['sku']}" />
            </div>

            <div class="form-field col-xs-12 col-sm-4">
                <label class="form-label" for="ctrl_price">Price</label>
                <input type="text" name="price" id="ctrl_price" class="form-input" value="${product['price']}" />
            </div>

            <div class="form-field col-xs-12 col-sm-4">
                <label class="form-label" for="ctrl_rental_tier">Rental Tier</label>
                <select id="ctrl_rental_tier" name="rental_tier" class="form-input">
                    <#list rentalTiers as rentalTier>
                        <option value="${rentalTier.name()}" <#if rentalTier == product['rental_tier']>selected</#if>>${rentalTier.label()}</option>
                    </#list>
                </select>
            </div>

            <div class="form-field col-xs-12 col-sm-4">
                <label class="form-label" for="ctrl_minimum_stock">Minimum Stock</label>
                <input type="number" name="minimum_stock" id="ctrl_minimum_stock" class="form-input" value="${product['minimum_stock']}" />
            </div>

            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_supplier_id">Supplier</label>
                <select id="ctrl_supplier_id" name="supplier_id" class="form-input">
                    <#list suppliers as supplier>
                        <option value="${supplier['id']}" <#if supplier['id'] == selectedSupplierId>selected</#if>>${supplier['name']}</option>
                    </#list>
                </select>
            </div>

            <div class="col-xs-12">
                <hr />
                <h3 class="form-section-header">Product Meta</h3>
            </div>

            <#if product['meta']??>
                <#if product['meta']['fields']??>
                    <#list product['meta']['fields'] as field>
                        <div class="form-field col-xs-6">
                            <label class="form-label" for="ctrl_meta_field">Field</label>
                            <input type="text" id="ctrl_meta_field" name="meta.fields.name[]" class="form-input" value="${field['name']}" />
                        </div>

                        <div class="form-field col-xs-6">
                            <label class="form-label" for="ctrl_meta_value">Value</label>
                            <input type="text" id="ctrl_meta_value" name="meta.fields.value[]" class="form-input" value="${field['value']}" />
                        </div>
                    </#list>
                </#if>
            </#if>

            <div class="field-copy">
                <div class="form-field col-xs-6">
                    <label class="form-label" for="ctrl_meta_field">Field</label>
                    <input type="text" id="ctrl_meta_field" name="meta.fields.name[]" class="form-input" />
                </div>

                <div class="form-field col-xs-6">
                    <label class="form-label" for="ctrl_meta_value">Value</label>
                    <input type="text" id="ctrl_meta_value" name="meta.fields.value[]" class="form-input" />
                </div>
            </div>

            <button type="submit" class="button primary">Save</button>
            <input type="hidden" name="_token" value="${fc.getSession().getCsrfToken()}" />

            <#if product['id'] != 0>
                <input type="hidden" name="product_id" value="${product['id']}" />
            </#if>
        </form>
    </div>
</div>