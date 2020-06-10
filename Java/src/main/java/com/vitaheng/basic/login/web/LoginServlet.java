package com.vitaheng.basic.login.web;

import com.vitaheng.basic.login.domain.User;
import com.vitaheng.basic.login.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map map = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
            User loginUser = new UserService().isLogin(user);
            if (loginUser==null) {
                req.setAttribute("msg","用户名或密码错误");
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }else {
                req.getSession().setAttribute("loginUser",loginUser);
                resp.sendRedirect("/index.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
