<%--
  Created by IntelliJ IDEA.
  User: Matvey
  Date: 18.04.2025
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="login-container">
    <h2>Авторизация</h2>
    <form action="/servlet/login" method="post">
        <input type="text" name="username" placeholder="Имя пользователя" required>
        <input type="password" name="password" placeholder="Пароль" required>
        <input type="submit" value="Войти">
    </form>
    <div class="link">
        <p>Нет аккаунта? <a href="register.jsp" class="button">Зарегистрироваться</a></p>
    </div>
    <div class="error">

    </div>
</div>
</body>
</html>
