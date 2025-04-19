<%--
  Created by IntelliJ IDEA.
  User: Matvey
  Date: 18.04.2025
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="registration-container">
    <h2>Регистрация</h2>
    <form action="/servlet/login" method="post">
        <input type="text" name="username" placeholder="Имя пользователя" required>
        <input type="password" name="password" placeholder="Пароль" required>
        <input type="email" name="e-mail" placeholder="Электронная почта" required>
        <input type="submit" value="Зарегистрироваться">
    </form>
    <div class="link">
        <p>Уже есть аккаунт? <a href="login.jsp" class="button">Авторизоваться</a></p>
    </div>
</div>
</body>
</html>
