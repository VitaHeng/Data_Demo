package com.vitaheng.basic.mybatis.mapper;

import com.vitaheng.basic.mybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserMapperTest {
    private UserMapper userMapper;

    @Before
    public void setUp() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

    }

    @Test
    public void queryUserById() {
        System.out.println(userMapper.queryUserById(1L));
    }

    @Test
    public void queryUserList() {
        List<User> users = userMapper.queryUserList();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void insertUser() throws ParseException {
        User user = new User();
        user.setUserName("zhang");
        user.setPassword("111");
        user.setName("heng");
        user.setAge(20);
        user.setSex(1);
        user.setBirthday(new SimpleDateFormat("yyyy-mm-dd").parse("1997-08-17"));
        userMapper.insertUser(user);
    }

    @Test
    public void updateUser() {
        User user = userMapper.queryUserById(1L);
        user.setName("heng_2");
        userMapper.updateUser(user);
    }

    @Test
    public void deleteById() {
        userMapper.deleteById(1L);
    }
}