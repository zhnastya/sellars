<#import '*/page.ftlh' as u>

<@u.page>
    <h4>Регистрация</h4>
    <form class="col-3" action="/registration" method="post">
        <input type="text" name="name" class="form-control mb-3" placeholder="Имя пользователя">
        <input type="email" name="email" class="form-control mb-3" placeholder="Эл. почта">
        <input type="text" name="phoneNumber" class="form-control mb-3" placeholder="Номер телефона">
        <input type="password" name="password" class="form-control mb-3" placeholder="Пароль">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" class="btn btn-primary" value="Зарегистрироваться"/>
    </form>
</@u.page>