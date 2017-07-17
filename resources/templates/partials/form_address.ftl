<#macro formAddress data street_1 street_2 city state zip country>
<div class="form-field col-xs-12">
    <label class="form-label" for="ctrl_${street_1}">Street Address</label>
    <input type="text" name="${street_1}" id="ctrl_${street_1}" class="form-input" value="${data[street_1]}" />
    <input type="text" name="${street_2}" id="ctrl_${street_2}" class="form-input" value="${data[street_2]}" />
</div>

<div class="form-field col-xs-12">
    <label class="form-label" for="ctrl_${city}">City</label>
    <input type="text" name="${city}" id="ctrl_${city}" class="form-input" value="${data[city]}" />
</div>

<div class="form-field col-xs-6">
    <label class="form-label" for="ctrl_${state}">State</label>
    <input type="text" name="${state}" id="ctrl_${state}" class="form-input" value="${data[state]}" />
</div>

<div class="form-field col-xs-6">
    <label class="form-label" for="ctrl_${zip}">Postal/Zip Code</label>
    <input type="text" name="${zip}" id="ctrl_${zip}" class="form-input" value="${data[zip]}" />
</div>

<div class="form-field col-xs-12">
    <label class="form-label" for="ctrl_${country}">Country</label>
    <select name="${country}" id="ctrl_${country}" class="form-input">
        <#list countries as c>
            <#if c?index != 0>
                <option value="${c.getAlpha2()}" <#if c.getAlpha2() == data[country]>selected</#if>>${c.getName()}</option>
            </#if>
        </#list>
    </select>
</div>
</#macro>