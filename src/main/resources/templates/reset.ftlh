<#import '*/page.ftlh' as u>

<@u.page>
    <div class="login">
        <div class="login__container">
            <h4 class="login__title">Восстановление пароля</h4>
            <form class="px-0" action="/login/reset" method="post">
                <input type="text" name="email" class="form-control mb-3" placeholder="Эл. почта">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="d-flex justify-content-center">
                    <input class="btn btn-primary" style="margin-right: 4px" type="submit" value="Восстановить"/>
                    <a class="btn btn-secondary" href="/login">Войти</a>
                </div>
            </form>
        </div>
    </div>

</@u.page>