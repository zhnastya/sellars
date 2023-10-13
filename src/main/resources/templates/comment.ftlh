<#import '*/page.ftlh' as u>

<@u.page>
    <div class="comments">
        <h3 class="title-comments">Комментарии (6)</h3>

        <ul class="media-list">
            <!-- Комментарий (уровень 1) -->
            <li class="media">
                <div class="media-left">
                    <a href="#">
                        <img class="media-object img-rounded" src="avatar1.jpg" alt="">
                    </a>
                </div>
                <div class="media-body">
                    <div class="media-heading">
                        <div class="author">Дима</div>
                        <div class="metadata">
                            <span class="date">16 ноября 2015, 13:43</span>
                        </div>
                    </div>
                    <div class="media-text text-justify">...</div>
                    <div class="footer-comment">
            <span class="vote plus" title="Нравится">
              <i class="fa fa-thumbs-up"></i>
            </span>
                        <span class="rating">+1</span>
                        <span class="vote minus" title="Не нравится">
              <i class="fa fa-thumbs-down"></i>
            </span>
                        <span class="devide">|</span>
                        <span class="comment-reply">
              <a href="#" class="reply">ответить</a>
            </span>
                    </div>
                    <!-- Вложенный медиа-компонент (уровень 2) -->
                    <div class="media">
                        <div class="media-left">...</div>
                        <div class="media-body">
                            <div class="media-heading">...</div>
                            <div class="media-text text-justify">...</div>
                            <div class="footer-comment">...</div>
                            <!-- Вложенный медиа-компонент (уровень 3) -->
                            <div class="media">
                                ...
                            </div><!-- Конец вложенного комментария (уровень 3) -->
                        </div>
                    </div><!-- Конец вложенного комментария (уровень 2) -->
                    <!-- Ещё один вложенный медиа-компонент (уровень 2) -->
                    <div class="media">
                        ...
                    </div><!-- Конец ещё одного вложенного комментария (уровень 2) -->
                </div>
            </li><!-- Конец комментария (уровень 1) -->
            <!-- Комментарий (уровень 1) -->
            <li class="media">
                ...
            </li><!-- Конец комментария (уровень 1) -->
            <!-- Комментарий (уровень 1) -->
            <li class="media">
                ...
            </li><!-- Конец комментария (уровень 1) -->
        </ul>
    </div>
    <#if comments??>
        <#list comments as comment>
            <p><b>${comment.authorId} | ${comment.description}</b> | ${comment.rating} | ${comment.timeOfCreated}
        </#list>
    <#else >
        Комментариев пока нет
    </#if>
    <h4 class="mt-5">Оставить комментарий </h4>
    <form class="col-5 px-0" action="/user/${user.id}/comment/save" method="post" enctype="multipart/form-data">
        <input type="text" name="rating" class="form-control mb-3" placeholder="Оставьте рейтинг">
        <input type="text" name="description" class="form-control mb-3" placeholder="Напишите комментарий">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="btn btn-primary" type="submit" value="Добавить комментарий"/>
    </form>
</@u.page>