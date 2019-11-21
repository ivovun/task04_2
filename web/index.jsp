<%--
  Created by IntelliJ IDEA.
  User: vladimirfilippov
  Date: 20/11/2019
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
    <form action="login" method="post">
        <label >Login:</label>
        <input type="text" name="login" size="45" />
        </br>
        <label >Password:</label>
        <input type="text" name="password" size="45" />
        </br>
        <input type="submit" value="Login" />
    </form>
    <h1> ${wrong_password_or_login} </h1>
</head>
<body>

</body>
</html>
