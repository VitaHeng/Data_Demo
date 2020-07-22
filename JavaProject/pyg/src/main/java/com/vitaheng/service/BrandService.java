package com.vitaheng.service;

import com.vitaheng.pojo.Brand;
import com.vitaheng.pojo.PageResult;

import java.util.List;

public interface BrandService {

    public List<Brand> queryBrandList();
    public PageResult queryPage(int pageNum,int pageSize);
    void insertBrand(Brand brand);
    void updateBrand(Brand brand);
    void deleteBrandById(Long[] ids);




}
