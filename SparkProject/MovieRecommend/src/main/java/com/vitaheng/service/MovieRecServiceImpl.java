package com.vitaheng.service;

import com.vitaheng.dao.MovieRecDao;
import com.vitaheng.pojo.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieRecServiceImpl implements MovieRecService {

    @Autowired
    private MovieRecDao movieRecDao;

    public void setMovieRecDao(MovieRecDao movieRecDao) {
        this.movieRecDao = movieRecDao;
    }

    public List<Recommend> getRateMoreMoviesRecom(int number) {
        return movieRecDao.getRateMoreMoviesRecom(number);
    }
}
