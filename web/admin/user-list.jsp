<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
    <style><%@include file="/css/style.css"%></style>
</head>
<body>

<div>

    <h1>User Management</h1>
    <a href="/logout">log out</a>

<%--    <h2>--%>
<%--        <a href="new">Add New User</a>--%>
<%--        &nbsp;&nbsp;&nbsp;--%>
<%--        <a href="list">List All Users</a>--%>

<%--    </h2>--%>

    <table>
        <caption><h2>List of Users</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Login</th>
            <th>password</th>
            <th>role</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="user" items="${listUser}">
            <tr>
                <td> ${user.id}</td>
                <td> ${user.name}</td>
                <td> ${user.login}</td>
                <td> ${user.password} </td>
                <td> ${user.role} </td>
                <td>
                    <input type="button" value="Edit" onclick="window.location.href='edit?id=${user.id}'" />
                    <a href="delete?id=${user.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <form action="insert" method="post">
                <td>Create new user</td>
                <td><input type="text" name="name"></td>
                <td><input type="text" name="login"></td>
                <td><input type="text" name="password"></td>
                <td>
                    <select name="role">
                        <option value="user" selected>User</option>
                        <option value="admin">Admin</option>
                    </select>
                </td>
                <td>
                    <input type="submit" value="Save"/>
                </td>
            </form>
    </tr>
    </table>
</div>
</body>
</html>
