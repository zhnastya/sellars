<#import '*/page.ftlh' as u>
<#import '*/pagination.ftlh' as a>

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
            <a href="/?offset=0&&limit=10&&category=Одежда">Одежда</a>
            <a href="/?offset=0&&limit=10&&category=Техника">Техника</a>
            <a href="/?offset=0&&limit=10&&category=Товары для дома">Товары для дома</a>
            <a href="/?offset=0&&limit=10&&category=Товары для детей">Товары для детей</a>
            <a href="/?offset=0&&limit=10&&category=Товары для животных">Товары для животных</a>
            <a href="/?offset=0&&limit=10&&category=Авто">Авто</a>
            <a href="/?offset=0&&limit=10&&category=Красота и здоровье">Красота и здоровье</a>
            <a href="/?offset=0&&limit=10&&category=Недвижимость">Недвижимость</a>
            <a href="/?offset=0&&limit=10&&category=Хобби и отдых">Хобби и отдых</a>
        </div>
        <div class="products__items">
            <#list products as product>
                <a href="/product/${product.id}" class="product">
                    <#if product.previewImageId??>
                        <div class="product__image" style="background-image: url('/images/${product.previewImageId}')"></div>
                    </#if>
                    <div class="product__info">
                        <div class="product__info-header">
                            <div class="product__title">
                                ${product.title}
                            </div>
                            <#if favorite??>
                            <i data-id="#{product.id}" class="favorite bi bi-star<#if favorite?map(p -> p.id)?seq_contains(product.id)>-fill</#if>"></i>
                            </#if>
                        </div>
                        <div class="product__info-bottom">
                            <div class="product__date">
                                ${product.dateOfCreated.format(formatter)}
                            </div>
                            <div class="product__price">
                                ${product.price} руб.
                            </div>
                        </div>
                    </div>
                </a>
            </#list>
        </div>
        <#assign query = '&sort=${sort}&category=${category}'>
        <@a.pages pages offset+1 query></@a.pages>
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

    document.querySelectorAll('.product .favorite').forEach(elem => {
        elem.addEventListener('click', async (ev) => {
            ev.preventDefault();
            const id = ev.currentTarget.getAttribute('data-id');
            const token = document.querySelector('meta[name="_csrf"]').content;
            const header = document.querySelector('meta[name="_csrf_header"]').content;
            const isLiked = elem.classList.contains('bi-star-fill')
            const endpoint = isLiked ? '/deleteFromJS' : '/likeFromJS'

            await fetch('/product/' + id + endpoint, {
                method: isLiked ? 'DELETE' : 'POST',
                credentials: 'include',
                headers: {'Content-Type': 'application/json', [header]: token},
            });

            window.location.reload();
        })
    });
</script>