<#function max x y>
    <#if (x<y)><#return y><#else><#return x></#if>
</#function>
<#function min x y>
    <#if (x<y)><#return x><#else><#return y></#if>
</#function>
<#macro pages totalPages p query>
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <#assign size = totalPages?size>
            <#assign limit = 10>
            <#if (p<=4)> <#-- p among first 5 pages -->
                <#assign interval = 1..(min(5,size))>
            <#elseif ((size-p)<4)> <#-- p among last 5 pages -->
                <#assign interval = (max(1,(size-4)))..size >
            <#else>
                <#assign interval = (p-2)..(p+2)>
            </#if>
            <#if !(interval?seq_contains(1))>
                <li class="page-item"><a class="page-link" href="/?offset=${0}&limit=${limit}${query}">1</a></li>
                <li class="page-item"><a class="page-link disabled">...</a></li>
                <#rt>
            </#if>
            <#list interval as page>
                <#if page=p>
                    <li class="page-item active"><a class="page-link" href="/?offset=${page-1}&limit=${limit}${query}">${page}</a></li>
                <#else>
                    <li class="page-item"><a class="page-link" href="/?offset=${page-1}&limit=${limit}${query}">${page}</a></li>
                </#if>
            </#list>
            <#if !(interval?seq_contains(size))>
                <li class="page-item"><a class="page-link disabled">...</a></li>
                <li class="page-item"><a class="page-link" href="/?offset=${size-1}&limit=${limit}${query}">${size-1}</a></li>
                <#lt>
            </#if>
        </ul>
    </nav>
</#macro>