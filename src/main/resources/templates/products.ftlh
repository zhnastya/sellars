<#import '*/page.ftlh' as u>

<@u.page>
    <h4>Товары со всей России</h4>
    <form class="col-5" action="/" method="get">
        <div class="input-group mb-3">
            <input type="text" name="title" class="form-control" placeholder="Введите название объявления" aria-label="Введите название объявления" aria-describedby="button-addon2">
            <button class="btn btn-outline-secondary" type="submit" id="button-addon2">Поиск</button>
        </div>
    </form>
    <#list products as product>
        <div>
            <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a></p>
        </div>
    <#else>
        <h4>Товаров нет</h4>
    </#list>
</@u.page>