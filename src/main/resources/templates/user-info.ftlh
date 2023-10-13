<#import '*/page.ftlh' as u>

<@u.page>
    <#if user.avatar??>
        <label class="avatar" for="avatar"
               style="background-image: url('/images/avatar/${user.avatar.id}')"></label>
    <#else>
        <label class="avatar" for="avatar">
            <i class="bi bi-person-fill"></i>
        </label>
    </#if>
    <dl class="row">
        <dt class="col-sm-3">Имя пользователя:</dt>
        <dd class="col-sm-9">${user.name}</dd>

        <dt class="col-sm-3">Почта:</dt>
        <dd class="col-sm-9">${user.email}</dd>

        <dt class="col-sm-3">Телефон:</dt>
        <dd class="col-sm-9">${user.phoneNumber}</dd>

        <dt class="col-sm-3">На продаване с</dt>
        <dd class="col-sm-9">${date}</dd>
    </dl>
    <div>
    <h4>Товары пользователя ${user.name}</h4>
    <#list products as product>
        <div>
            <#if product.previewImageId??>
                <img src="/images/${product.previewImageId}" style="width:150px; " alt="">
            </#if>
            <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a></p>
        </div>
    <#else>
        <h3>Товаров нет</h3>
    </#list>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</@u.page>