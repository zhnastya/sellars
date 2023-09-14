<!DOCTYPE html>
<html>
<head>
    <title>SELLARS</title>
</head>
<body>
<h1>SELLARS</h1><hr>
<h4>Авторизация</h4>
<form action="/registration" method="post">
    Эл. почта: <input type="email" name="username"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
<a href="/registration">Зарегистрироваться</a>
</body>
</html>