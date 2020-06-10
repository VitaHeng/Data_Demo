package com.vitaheng.basic.mybatis.mapper;

import com.vitaheng.basic.mybatis.domain.User;

import java.util.List;

public interface UserMapper {
    public User queryUserById(Long id);
    public List<User> queryUserList();
    public void insertUser(User user);
    public void updateUser(User user);
    public void deleteById(Long id);
}
