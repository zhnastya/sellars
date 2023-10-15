<div class="d-flex align-items-center">
    <a href="/my-room" class="header-user">
        <#if user.avatar??>
        <div class="header-user__avatar" style="background-image: url('/images/avatar/${user.avatar.id}')"></div>
        <#else>
            <div class="header-user__avatar"><i class="bi bi-person-fill"></i></div>
        </#if>
        <div class="header-user__name">
            ${user.name}
        </div>
    </a>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="btn btn-primary" type="submit" value="Выйти"/>
    </form>
</div>
