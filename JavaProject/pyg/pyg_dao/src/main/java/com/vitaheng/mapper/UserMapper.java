package com.vitaheng.mapper;

import com.vitaheng.pojo.User;

import java.util.List;

public interface UserMapper {
    public User queryUserById(Long id);
    public void deleteById(Long id);
}
