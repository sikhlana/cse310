<li id="item-${product['id']}">
    <div class="anchor">
        <span class="col-xs-2">${product['sku']}</span>
        <span class="col-xs-4">${product['title']}</span>
        <span class="col-xs-2">${product['price']}</span>

        <form class="ajax-form qty-form col-xs-2" action="${adminlink('pos/set-qty')}" method="post">
            <input type="hidden" name="product_id" value="${product['id']}" />
            <input type="number" value="${quantity}" name="quantity" placeholder="Quantity..." />
            <input type="submit" style="display: none">
        </form>
    </div>

    <a class="delete-item delete-button" href="${adminlink("pos/delete-item")}?id=${product['id']}">
        <i class="fa fa-times"></i>
    </a>
</li>