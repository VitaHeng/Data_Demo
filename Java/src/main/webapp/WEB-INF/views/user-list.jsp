<%--
  Created by IntelliJ IDEA.
  User: W9002778
  Date: 2020/6/12
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach items="${users}" var="user">
<tr>
    <td>${user.id}</td>
    <td>${user.userName}</td>
    <td>${user.password}</td>
    <td>${user.name}</td>
</tr>
</c:forEach>
</body>
</html>