package com.vitaheng.service;

import com.vitaheng.pojo.Recommend;

import java.util.List;

public interface MovieRecService {
    public List<Recommend> getRateMoreMoviesRecom(int number);

}
