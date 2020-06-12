<%--
  Created by IntelliJ IDEA.
  User: VitaHeng
  Date: 2020/6/2
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>登陆页面</title>
</head>
<body>
<form method="get" action="/LoginServlet">
    <table>
        <tr>
            <td colspan="2">
                ${msg}
            </td>
        </tr>
        <tr>
            <td>用户名</td>
            <td>
                <input type="text" name="username" />
            </td>
        </tr>
        <tr>
            <td>密码</td>
            <td>
                <input type="password" name="password">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="登陆">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
