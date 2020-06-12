package com.vitaheng.basic.spring.dao;

import com.vitaheng.basic.spring.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class BookDaoTest {
    @Value("#{bookDao}")
    private BookDao bookDao;
    Book book = new Book();

    @Test
    public void save() {
        book.setName("书剑恩仇录");
        book.setPrice(9.9);
        bookDao.save(book);
    }

    @Test
    public void update() {
        book.setId(3);
        book.setName("天龙八部");
        book.setPrice(5);
        bookDao.update(book);
    }

    @Test
    public void delById() {
        bookDao.delById(1);
    }

    @Test
    public void findNameById() {
        String name = bookDao.findNameById(3);
        System.out.println(name);
    }
}