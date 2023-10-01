<div class="d-flex align-items-center">
    <a href="/my_room" class="header-user">
        <div class="header-user__avatar" style="background-image: url('/images/avatar/${user.avatar.id}')">
        </div>
        <div class="header-user__name">
            ${user.name}
        </div>
    </a>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="btn btn-primary" type="submit" value="Выйти"/>
    </form>
</div>
