<div class="header border border-bottom-1 py-2 mb-2">
    <div class="container">
        <div class="d-flex align-items-center justify-content-between">
            <div>
                <a href="/" class="link-underline link-underline-opacity-0 h1 text-primary">Продаван</a>
            </div>
            <div>
                <#if user??>
                    <#if user.email??>
                        <#include "*/headerUser.ftlh">
                        <#else>
                            <a class="btn btn-primary" href="/login">Войти</a>
                    </#if>
                    <#else>
                        <a class="btn btn-primary" href="/login">Войти</a>
                </#if>
            </div>
        </div>
    </div>
</div>
