<#import '*/page.ftlh' as u>

<@u.page>
    <div class="d-flex justify-content-between align-items-start">
        <form class="col-5" action="/?title=${title}" method="get">
            <div class="input-group mb-3">
                <input type="text" name="title" class="form-control" placeholder="Введите название объявления"
                       aria-label="Введите название объявления" aria-describedby="button-addon2">
                <button class="btn btn-outline-secondary" type="submit" id="button-addon2">Поиск</button>
            </div>
        </form>
        <select class="form-select" id="sortSelect">
            <option value="" disabled hidden>Сортировка</option>
            <option value="0">Цена (по возрастанию)</option>
            <option value="1">Цена (по убыванию)</option>
            <option value="2">Дата (сначала новые)</option>
            <option value="3">Дата (сначала старые)</option>
        </select>
    </div>
    <div class="products">
        <div class="categories">
            <a href="/?offset=0&&limit=5&&category=Одежда">Одежда</a>
            <a href="/?offset=0&&limit=5&&category=Техника">Техника</a>
            <a href="/?offset=0&&limit=5&&category=Товары для дома">Товары для дома</a>
            <a href="/?offset=0&&limit=5&&category=Товары для детей">Товары для детей</a>
            <a href="/?offset=0&&limit=5&&category=Товары для животных">Товары для животных</a>
            <a href="/?offset=0&&limit=5&&category=Авто">Авто</a>
            <a href="/?offset=0&&limit=5&&category=Красота и здоровье">Красота и здоровье</a>
            <a href="/?offset=0&&limit=5&&category=Недвижимость">Недвижимость</a>
            <a href="/?offset=0&&limit=5&&category=Хобби и отдых">Хобби и отдых</a>
        </div>
        <div class="products__items">
               <#list products as product>
                   <a href="/product/${product.id}" class="product">
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
        <#if pageSize!=0>
            <nav aria-label="Page navigation example">
              <ul class="pagination" >
                <#if offset-1 < 0>
                <#else >
                    <li class="page-item"><a class="page-link" href="/?offset=${offset-1}&&limit=5">Назад</a></li>
                </#if>
                <#list pages as page>
                    <li class="page-item"><a class="page-link" href="/?offset=${page}&&limit=5">${page+1}</a></li>
                </#list>
                <#if offset+1 < pageSize>
                    <li class="page-item"><a class="page-link" href="/?offset=${offset+1}&&limit=5">Вперед</a></li>
                <#else>
                </#if>

              </ul>
            </nav>
        </#if>
    </div>
</@u.page>

<script lang="text/javascript">
    const currentUrl = new URL(location.href);
    const sort = currentUrl.searchParams.get("sort");
    document.querySelector('#sortSelect').value = sort || "";

    document.querySelector('#sortSelect').addEventListener('input', (ev) => {
        const url = new URL(location.href);
        url.searchParams.set('sort', ev.target.value);
        window.location = url.href;
    });
</script>