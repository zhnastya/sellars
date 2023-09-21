<div class="d-flex align-items-center">
    <div class="me-3">
        <a href="/my_room">${user.name}</a>
        <#if user.avatar??>
            <style>
                .round {
                    width: 50px; height: 50px;
                    border-radius: 50%;
                    border: 3px solid darkblue;
                }
            </style>
            <img src="/images/avatar/${user.avatar.id}" alt="" align="right" class="round">
        </#if>
    </div>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="btn btn-primary" type="submit" value="Выйти"/>
    </form>
</div>
