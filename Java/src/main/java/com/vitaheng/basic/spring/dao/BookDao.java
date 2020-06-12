package com.vitaheng.basic.spring.dao;

import com.vitaheng.basic.spring.domain.Book;

import java.util.List;

public interface BookDao {
    public void save(Book book);
    public void update(Book book);
    public void delById(int id);
    public String findNameById(int id);
}
