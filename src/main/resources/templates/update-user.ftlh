<#import '*/page.ftlh' as u>

<@u.page>
    <div class="login">
        <div class="login__container">
            <h4 class="login__title">Изменение профиля</h4>
            <form class="px-0" action="/my-room/change/user" method="post">
                <input type="text" name="name" class="form-control mb-3" placeholder="Имя пользователя">
                <input type="email" name="email" class="form-control mb-3" placeholder="Эл. почта">
                <input type="text" name="phoneNumber" class="form-control mb-3" placeholder="Номер телефона">
                <input type="file" name="file" class="form-control mb-3" placeholder="Аватар">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="d-flex justify-content-center">
                    <input type="submit" class="btn btn-primary" value="Сохранить изменение"/>
                </div>
            </form>
        </div>
    </div>
</@u.page>