<#import '*/page.ftlh' as u>

<@u.page>
    <div class="login">
        <div class="login__container">
            <h4 class="login__title">Регистрация</h4>
            <form class="px-0" action="/registration" method="post">
                <input type="text" name="name" class="form-control mb-3" placeholder="Имя пользователя">
                <input type="email" name="email" class="form-control mb-3" placeholder="Эл. почта">
                <input type="text" name="phoneNumber" class="form-control mb-3" placeholder="Номер телефона">
                <input type="password" name="password" class="form-control mb-3" placeholder="Пароль">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="d-flex justify-content-center">
                    <input type="submit" class="btn btn-primary" value="Зарегистрироваться"/>
                </div>
            </form>
        </div>
    </div>
</@u.page>