<#import '*/page.ftlh' as u>
<@u.page>
    <div class="products__items">
        <#list products as product>
            <a href="/my-room/product/${product.id}/info" class="product">
                <#if product.previewImageId??>
                    <div class="product__image" style="background-image: url('/images/${product.previewImageId}')"></div>
                </#if>
                <div class="product__info">
                    <div class="product__title">
                        ${product.title}
                    </div>
                    <div class="product__price">
                        ${product.price} руб.
                    </div>
                </div>
            </a>
        </#list>
    </div>
</@u.page>