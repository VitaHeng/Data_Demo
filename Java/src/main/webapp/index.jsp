<%@ page import="com.vitaheng.basic.login.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: Liu
  Date: 2018/9/10
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>首页</title>
  </head>
  <body>
    <%--脚本片段--%>
    <%
      //1. 获取Session容器的id.
      HttpSession hs = request.getSession();

      //6. 设置存储SessionId的那个cookie的生命周期.
      Cookie cookie = new Cookie("JSESSIONID",hs.getId());
      cookie.setMaxAge(6000);
      response.addCookie(cookie);

      //2. 从中获取: loginUser对象.
      User user = (User) hs.getAttribute("loginUser");

      //3. 判断该对象是否存在.
      if (user != null) {
        //4. 存在: 提示, 欢迎, ×××
    %>
      <%="欢迎, " + user.getNickname() %>
    <%--注销的思路就是: 销毁该Session容器, Session容器都没有了,里边的User对象肯定没有--%>
      <a href="/invalidate.jsp">注销</a>
    <%
      } else {
        //5. 不存在: 提示, 请先登录, 然后操作.
     %>
      <h3><a href="/login.jsp">请先登录, 然后操作.</a></h3>
    <%
      }
    %>
  </body>
</html>
