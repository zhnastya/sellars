<div class="header py-2 mb-2">
    <div class="container">
        <div class="d-flex align-items-center justify-content-between">
            <a href="/?offset=0&&limit=10" class="logo d-flex align-items-center">
                <img class="logo__image" src="/../../../../static/hand.png">
                <div class="logo__text">
                    <div class="logo__title">prodavan</div>
                    <div class="logo__subtitle">Товары со всей России</div>
                </div>
            </a>
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
