<#import '*/page.ftlh' as u>

<@u.page>
    <h2>Личный кабинет</h2>
    <#if image??>
        <img src="/images/avatar/${image.id}" style="width:200px; " alt="" align="right">
    <#else>
        <form class="col-5 px-0" action="/my_room" method="post" enctype="multipart/form-data">
            <input type="file" name="file" class="form-control mb-3" placeholder="Загрузитe фото">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input class="btn btn-primary" type="submit" value="Добавить аватар"/>
        </form>
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
    <h4>Ваши товары:</h4>
    <#list products as product>
        <div>
            <#if product.previewImageId??>
            <img src="/images/${product.previewImageId}" style="width:150px; ">
            <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a></p>
                <#else>
                    <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a></p>
            </#if>
        </div>
    <#else>
        <h3>Товаров нет</h3>
    </#list>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
    <h4 class="mt-5">Создать новый товар</h4>
    <form class="col-5 px-0" action="/product/create" method="post" enctype="multipart/form-data">
        <input type="text" name="title" class="form-control mb-3" placeholder="Название объявления">
        <input type="text" name="description" class="form-control mb-3" placeholder="Описание объявления">
        <input type="number" name="price" class="form-control mb-3" placeholder="Цена">
        <input type="text" name="city" class="form-control mb-3" placeholder="Город">
        <label>
            <select name="category" class="form-control mb-3" >
                <option disabled>Категория товара</option>
                <option value="Одежда">Одежда</option>
                <option value="Техника">Техника</option>
                <option value="Товары для дома">Товары для дома</option>
                <option value="Товары для детей">Товары для детей</option>
                <option value="Товары для животных">Товары для животных</option>
                <option value="Авто">Авто</option>
                <option value="Красота и здоровье">Красота и здоровье</option>
                <option value="Недвижимость">Недвижимость</option>
                <option value="Хобби и отдых">Хобби и отдых</option>
            </select>
        </label>
        <input type="file" name="file1" class="form-control mb-3" placeholder="Первая фотография">
        <input type="file" name="file2" class="form-control mb-3" placeholder="Вторая фотография">
        <input type="file" name="file3" class="form-control mb-3" placeholder="Третья фотография">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="btn btn-primary" type="submit" value="Добавить товар"/>
    </form>
</@u.page>