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
            <form class="col-5 px-0" action="/product/${product.id}/like" method="post" enctype="multipart/form-data">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input class="btn btn-primary" type="submit" value="Лайкнуть товар"/>
            </form>
            <div class="d-flex align-items-center product-info__data-bottom">
                <a href="<#if user??>/user/${productUser.id}<#else>/login</#if>" class="header-user">
                    <#if productUser.avatar??>
                        <div class="header-user__avatar" style="background-image: url('/images/avatar/${productUser.avatar.id}')"></div>
                    <#else>
                        <div class="header-user__avatar"><i class="bi bi-person-fill"></i></div>
                    </#if>
                    <div class="header-user__name">
                        ${productUser.name}
                    </div>
                </a>
                <div class="rating-result">
                    <#list 1..5 as x>
                        <#if x <= rating>
                            <span class="active"></span>
                        <#else>
                            <span></span>
                        </#if>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</@u.page>

<script type="text/javascript">

</script>