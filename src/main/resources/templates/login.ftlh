<#import '*/page.ftlh' as u>

<@u.page>
    <h4>Авторизация</h4>
    <form class="col-3 px-0" action="/login" method="post">
        <input type="email" name="username" class="form-control mb-3" placeholder="Эл. почта">
        <input type="password" name="password" class="form-control mb-3" placeholder="Пароль">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="btn btn-primary" type="submit" value="Вход"/>
    </form>
    <a href="/registration">Зарегистрироваться</a>
</@u.page>