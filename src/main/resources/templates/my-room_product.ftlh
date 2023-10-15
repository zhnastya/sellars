<#import '*/page.ftlh' as u>

<@u.page>
    <div class="product-info">
        <div class="product-info__title">
            ${product.title}
        </div>
        <div class="product-info__price">
            ${product.price} руб.
        </div>
        <div id="carouselExample" class="carousel slide">
            <div class="carousel-inner">
                <#list images as img>
                    <div class="carousel-item <#if img?index == 0>active</#if>" style="height: 200px">
                        <div class="carousel-item__image d-flex justify-content-center w-100 h-100">
                            <img src="/images/${img.id}" class="d-block h-100" />
                        </div>
                        <img src="/images/${img.id}" class="back-image" />
                    </div>
                </#list>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <div class="product-info__data">
            <div class="product-info__data-groups">
                <div class="group">
                    <div class="group__title">
                        Описание товара:
                    </div>
                    <div class="group__text">
                        ${product.description}
                    </div>
                </div>
                <div class="group">
                    <div class="group__title">
                        Город:
                    </div>
                    <div class="group__text">
                        ${product.city}
                    </div>
                </div>
            </div>
        </div>
    </div>
    <a href="/my-room/change/product/${product.id}">Изменить товар:</a>
</@u.page>