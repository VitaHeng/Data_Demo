package com.vitaheng.service;

import com.vitaheng.mapper.BrandMapper;
import com.vitaheng.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> queryBrandList() {
        return brandMapper.queryBrandList();
    }
}
