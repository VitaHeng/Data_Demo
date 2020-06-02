package com.vitaheng.login.service;

import com.vitaheng.login.dao.UserDao;
import com.vitaheng.login.domain.User;

public class UserService {
    public User isLogin(User user) {
        return new UserDao().isLogin(user);
    }
}
