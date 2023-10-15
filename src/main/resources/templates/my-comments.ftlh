<#import '*/page.ftlh' as u>

<@u.page>
    <#if comments??>
        <#list comments as comment>
            <p><b>${comment.description}</b> | ${comment.rating} | ${comment.timeOfCreated}
        </#list>

    <#else >
        Комментариев пока нет
    </#if>
</@u.page>