<#import '*/page.ftlh' as u>

<@u.page>
    <div class="lk">
        <h2>Личный кабинет</h2>
        <div class="lk__container">
            <div class="user-info">
                <form id="avatar-form" class="" action="/my-room/change/avatar" method="post"
                      enctype="multipart/form-data">
                    <input type="file" name="file" hidden="hidden" id="avatar" placeholder="Загрузитe фото">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <#if image??>
                        <label class="avatar" for="avatar"
                               style="background-image: url('/images/avatar/${image.id}')"></label>
                    <#else>
                        <label class="avatar" for="avatar">
                            <i class="bi bi-person-fill"></i>
                        </label>
                    </#if>
                    <div class="user-info-links">
                        <a href="/my-room/change/user" class="btn btn-light">Изменить профиль</a>
                    </div>
                </form>
                <div class="user-info__right">
                    <div class="user-info__data">
                        <div class="rodjer">
                            <img src="/static/rodjer.gif" alt="rodjer"/>
                        </div>
                        <div class="group">
                            <div class="group__title">
                                Имя:
                            </div>
                            <div class="group__text">
                                ${user.name}
                            </div>
                        </div>
                        <div class="group">
                            <div class="group__title">
                                Email:
                            </div>
                            <div class="group__text">
                                ${user.email}
                            </div>
                        </div>
                        <div class="group">
                            <div class="group__title">
                                Имя:
                            </div>
                            <div class="group__text">
                                ${user.name}
                            </div>
                        </div>
                        <div class="group">
                            <div class="group__title">
                                Телефон:
                            </div>
                            <div class="group__text">
                                ${user.phoneNumber}
                            </div>
                        </div>
                        <div class="group">
                            <div class="group__title">
                                Дата регистрации:
                            </div>
                            <div class="group__text">
                                ${date}
                            </div>
                        </div>
                    </div>
                    <div class="user-info__methods">
                        <a href="/my-room/products" class="btn btn-light">Ваши товары</a>
                        <a href="/my-room/create/product" class="btn btn-light">Создать товар</a>
                        <a href="/my-room/like-products" class="btn btn-light">Лайкнутые товары</a>
                        <a href="/my-room/comments" class="btn btn-light">Мои комментарии</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@u.page>

<script>
    document.querySelector('#avatar').addEventListener('input', (ev) => {
        document.querySelector('#avatar-form').submit();
    });
    document.querySelector('.rodjer').addEventListener('click', (ev) => {
        ev.currentTarget.classList.toggle('-negativ-');
    });
</script>