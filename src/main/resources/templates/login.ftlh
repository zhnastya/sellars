<#import '*/page.ftlh' as u>

<@u.page>
    <div class="login">
        <div class="login__container">
            <h4 class="login__title">Авторизация</h4>
            <form class="px-0" action="/login" method="post">
                <input type="email" name="username" class="form-control mb-3" placeholder="Эл. почта">
                <input type="password" name="password" class="form-control mb-3" placeholder="Пароль">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="d-flex justify-content-center">
                    <input class="btn btn-primary" style="margin-right: 4px" type="submit" value="Вход"/>
                    <a class="btn btn-secondary" href="/registration">Зарегистрироваться</a>
                </div>
            </form>
        </div>
    </div>

</@u.page>