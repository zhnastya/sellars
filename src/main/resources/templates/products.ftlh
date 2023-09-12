<!DOCTYPE html>
<html>
<head>
    <title>SELLARS</title>
</head>
<body>
<h1>SELLARS</h1><hr>
    <h4> Все товары </h4>
    <#list products as product>
    <div>
        <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/#{product.id}">Подробнее...</p>
    </div>
</#list>
<hr>
<h3>
    Создать новый товар
</h3>
<form action="/product/create" method="post">

</form>
</body>
</html>