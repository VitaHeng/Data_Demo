package com.vitaheng.login.dao;

import com.vitaheng.jdbc.utils.C3P0Utils;
import com.vitaheng.login.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDao {
    public User isLogin(User user) {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select * from users where username=? and password=?";
        try{
            return qr.query(sql,new BeanHandler<>(User.class),user.getUsername(),user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
