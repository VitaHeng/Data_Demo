package com.vitaheng.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class UserMapperTest {

    private UserMapper userMapper;
    @Before
    public void setUp() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
         userMapper = sqlSession.getMapper(UserMapper.class);

    }


    @org.junit.Test
    public void deleteById() {
        userMapper.deleteById(1L);
    }

    @Test
    public void queryUserById() {
        System.out.println(userMapper.queryUserById(1L));
    }

}