<#import '*/page.ftlh' as u>

<@u.page>
    <div class="user-info" style="margin-top: 16px;">
        <div>
            <form id="avatar-form" class="">
                <#if user.avatar??>
                    <label class="avatar" for="avatar"
                           style="background-image: url('/images/avatar/${user.avatar.id}')"></label>
                <#else>
                    <label class="avatar" for="avatar">
                        <i class="bi bi-person-fill"></i>
                    </label>
                </#if>
                <div class="rating-result">
                    <#list 1..5 as x>
                        <#if x <= rating>
                            <span class="active"></span>
                        <#else>
                            <span></span>
                        </#if>
                    </#list>
                </div>
                <div class="text-center">
                    На основании ${comments?size} оценок
                </div>
            </form>
            <div class="user-info__methods" style="margin-top: 48px;">
                <a href="/my-room/products" class="btn btn-light">Завершенные сделки</a>
                <a href="/my-room/create/product" class="btn btn-light">Актуальные товары</a>
            </div>
        </div>
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
            <div class="standard-block">
                <div class="comments">
                    <div class="comments__title">
                        Комментарии
                    </div>
                    <#if comments?size == 0>
                        <div class="comments__empty">
                            Комментариев нет...
                        </div>
                    <#else>
                    <div class="comments-list">
                        <#list comments as comment>
                            <div class="comment">
                                <div class="comment__header">
                                    <a href="/user/${comment.author.id}" class="comment__author">
                                        <#if comment.author.avatar??>
                                            <div
                                               class="comment__author-avatar"
                                               style="background-image: url('/images/avatar/${comment.author.avatar.id}')"
                                            >
                                            </div>
                                        <#else>
                                            <div class="comment__author-avatar">
                                                <i class="bi bi-person-fill"></i>
                                            </div>
                                        </#if>
                                        <div class="comment__author-name">
                                            ${comment.author.name}
                                        </div>
                                    </a>
                                </div>
                                <div class="comment__data">
                                    <div class="comment__description">
                                        ${comment.description}
                                    </div>
                                </div>
                                <div class="comment__bottom">
                                    <div class="rating-result -sm-">
                                        <#list 1..5 as x>
                                            <#if x <= comment.rating>
                                                <span class="active"></span>
                                            <#else>
                                                <span></span>
                                            </#if>
                                        </#list>
                                    </div>
                                    <div class="comment__date">
                                        ${comment.timeOfCreated.format(formatter)}
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@u.page>