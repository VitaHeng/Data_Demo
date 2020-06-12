package com.vitaheng.basic.spring.dao;

import com.vitaheng.basic.spring.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import java.util.List;

public class BookDaoImpl implements BookDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Book book) {
        String sql = "insert into book values(null,?,?)";
        Object[] paramArr = {book.getName(),book.getPrice()};
        jdbcTemplate.update(sql,paramArr);
    }

    @Override
    public void update(Book book) {
        String sql = "update book set name=?,price=? where id=?";
        Object[] param = {book.getName(),book.getPrice(),book.getId()};
        jdbcTemplate.update(sql,param);
    }

    @Override
    public void delById(int id) {
        String sql="delete from book where id=?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public String findNameById(int id) {
        String sql = "select name from book where id=?";
        return jdbcTemplate.queryForObject(sql,String.class,id);
    }
}
