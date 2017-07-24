${container("title", "Point of Sale")}

<div class="container">
    <div class="row">
         <div class="col-xs-12">
            <h1 class="page-title">Point of Sale</h1>

             <div class="top-ctrl">
                 <a class="button small primary" href="${adminlink('pos/clear')}">Clear Cart</a>
             </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-6">
                    <form class="ajax-form sku-search-form" action="${adminlink("pos/search")}" method="post">
                        <input type="search" autocomplete="false" name="sku" class="input-field" placeholder="Product SKU..." />
                        <button type="submit"><i class="fa fa-long-arrow-right"></i></button>
                    </form>
                </div>

                <div class="col-sm-6">
                    <form class="ajax-form user-search-form" action="${adminlink("pos/set-user")}" method="post">
                        <input type="search" autocomplete="false" name="sku" class="input-field" placeholder="User ID" />
                        <button type="submit"><i class="fa fa-long-arrow-right"></i></button>
                    </form>
                </div>
            </div>

            <div class="list-header">
                <h3>
                    <span class="col-xs-2">SKU</span>
                    <span class="col-xs-3">Title</span>
                    <span class="col-xs-3">Price</span>
                    <span class="col-xs-2">Quantity</span>
                </h3>
            </div>

            <ol id="pos-item-list" class="item-list">

                <#list products as item>
                    <#assign product=item['product'] quantity=item['quantity']>
                    <#include './pos/list-item.ftl'>
                </#list>

            </ol>

            <div class="list-footer">

            </div>
        </div>

        <div class="col-sm-3">
            <h3 class="page-title">Cart Details</h3>

            <dl>
                <dt>Total Price</dt>
                <dd class="cart-price">${total}</dd>
            </dl>

            <dl>
                <dt>Discount</dt>
                <dd class="cart-discount">0</dd>
            </dl>

            <a class="button primary large" href="${adminlink('pos/checkout')}">Checkout</a>
        </div>
    </div>
</div>