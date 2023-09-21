<#import '*/page.ftlh' as u>

<@u.page>
    <h4 class="mb-3">Подробная информация о товаре</h4>

    <div id="carouselExample" class="carousel slide mb-2">
        <div class="carousel-inner">
            <#list images as img>
                <div class="carousel-item <#if img?index == 0>active</#if>" style="height: 200px">
                    <div class="carousel-item__image d-flex justify-content-center w-100 h-100">
                        <img src="/images/${img.id}" class="d-block h-100" />
                    </div>
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

    <dl class="row">
        <dt class="col-sm-3">Название товара:</dt>
        <dd class="col-sm-9">${product.title}</dd>

        <dt class="col-sm-3">Описание товара:</dt>
        <dd class="col-sm-9">${product.description}</dd>

        <dt class="col-sm-3">Цена:</dt>
        <dd class="col-sm-9">${product.price}</dd>

        <dt class="col-sm-3 text-truncate">Город:</dt>
        <dd class="col-sm-9">${product.city}</dd>

        <dt class="col-sm-3">Автор:</dt>
    </dl>
    <a href="<#if user.email??>/user/${user.id}<#else>/login</#if>">${user.name}</a>
</@u.page>