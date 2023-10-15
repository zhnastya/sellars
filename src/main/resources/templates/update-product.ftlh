<#import '*/page.ftlh' as u>

<@u.page>
    <h4 class="mt-5">Обновить товар</h4>
    <form class="col-5 px-0" action="/my-room/change/product/${product.id}" method="post" enctype="multipart/form-data">
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
        <input class="btn btn-primary" type="submit" value="Сохранить изменения"/>
    </form>
</@u.page>