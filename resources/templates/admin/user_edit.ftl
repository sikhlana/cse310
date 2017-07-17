<#if user['id'] == 0>
    <#assign title="Add New User">
<#else>
    <#assign title="Edit User: " + user['name']>
</#if>

${container("title", title)}

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1 class="page-title">${title}</h1>
        </div>
    </div>

    <div class="row">
        <form class="form ajax-form" action="${adminlink('users/save')}" method="post">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active" role="presentation"><a href="#basic" role="tab" data-toggle="tab">Basic Info</a></li>
                <li role="presentation"><a href="#billing" role="tab" data-toggle="tab">Billing Address</a></li>
                <li role="presentation"><a href="#shipping" role="tab" data-toggle="tab">Shipping Address</a></li>
            </ul>

            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="basic">
                    <div class="form-field col-xs-12">
                        <label class="form-label" for="ctrl_name">Name</label>
                        <input type="text" name="name" id="ctrl_name" class="form-input" value="${user['name']}" />
                    </div>

                    <div class="form-field col-xs-12">
                        <label class="form-label" for="ctrl_phone_number">Phone Number</label>
                        <input type="tel" name="phone_number" id="ctrl_phone_number" class="form-input" value="${user['phone_number']}" />
                    </div>

                    <div class="form-field col-xs-12">
                        <label class="form-label" for="ctrl_email">Email</label>
                        <input type="email" name="email" id="ctrl_email" class="form-input" value="${user['email']}" />
                    </div>

                    <div class="form-field col-xs-12">
                        <label class="form-label"><input type="checkbox" <#if user['is_staff']>checked="checked"</#if> name="is_staff" value="yes" /> Is a staff member.</label>
                    </div>
                </div>

                <#include '../partials/form_address.ftl'>

                <div role="tabpanel" class="tab-pane" id="billing">
                    <@formAddress user "billing_street_1" "billing_street_2" "billing_city" "billing_state" "billing_zip" "billing_country" />
                </div>

                <div role="tabpanel" class="tab-pane" id="shipping">
                    <@formAddress user "shipping_street_1" "shipping_street_2" "shipping_city" "shipping_state" "shipping_zip" "shipping_country" />
                </div>
            </div>

            <button type="submit" class="button primary">Save</button>
            <input type="hidden" name="_token" value="${fc.getSession().getCsrfToken()}" />

            <#if user['id'] != 0>
                <input type="hidden" name="user_id" value="${user['id']}" />
            </#if>
        </form>
    </div>
</div>