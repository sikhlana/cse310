<#if supplier['id'] == 0>
    <#assign title="Add New Supplier">
<#else>
    <#assign title="Edit Supplier: " + supplier['name']>
</#if>

${container("title", title)}

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1 class="page-title">${title}</h1>
        </div>
    </div>

    <div class="row">
        <form class="form product-form" action="${adminlink('suppliers/save')}" method="post">
            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_name">Name</label>
                <input type="text" name="name" id="ctrl_name" class="form-input" value="${supplier['name']}" />
            </div>

            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_phone_number">Phone Number</label>
                <input type="tel" name="phone_number" id="ctrl_phone_number" class="form-input" value="${supplier['phone_number']}" />
            </div>

            <div class="form-field col-xs-12">
                <label class="form-label" for="ctrl_email">Email</label>
                <input type="email" name="email" id="ctrl_email" class="form-input" value="${supplier['email']}" />
            </div>

            <div class="col-xs-12">
                <hr />
            </div>

            <#include '../partials/form_address.ftl'>
            <@formAddress supplier "address_street_1" "address_street_2" "address_city" "address_state" "address_zip" "address_country" />

            <button type="submit" class="button primary">Save</button>
            <input type="hidden" name="_token" value="${fc.getSession().getCsrfToken()}" />

            <#if supplier['id'] != 0>
                <input type="hidden" name="supplier_id" value="${supplier['id']}" />
            </#if>
        </form>
    </div>
</div>