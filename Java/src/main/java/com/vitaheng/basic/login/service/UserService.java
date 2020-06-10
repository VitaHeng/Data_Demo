package com.vitaheng.basic.login.service;

import com.vitaheng.basic.login.dao.UserDao;
import com.vitaheng.basic.login.domain.User;

public class UserService {
    public User isLogin(User user) {
        return new UserDao().isLogin(user);
    }
}
