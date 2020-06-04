<%--
  Created by IntelliJ IDEA.
  User: Liu
  Date: 2018/9/10
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注销</title>
</head>
<body>
<%
    //销毁session
    session.invalidate();

    //重定向 到login.jsp(登陆页面)
    response.sendRedirect("/login.jsp");
%>

</body>
</html>
