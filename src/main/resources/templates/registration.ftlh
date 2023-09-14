<!DOCTYPE html>
<html>
<head>
    <title>SELLARS</title>
</head>
<body>
<h1>SELLARS</h1><hr>
<h4>Авторизация</h4>
<form action="/login" method="post">
    Имя пользователя: <input type="text" name="username"><br><br>
    Эл. почта: <input type="email" name="email"><br><br>
    Номер телефона: <input type="text" name="phoneNumber"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Регистрация"/>
</form>
<a href="/registration">Зарегистрироваться</a>
</body>
</html>