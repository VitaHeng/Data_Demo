package com.vitaheng.mapper;

import com.vitaheng.pojo.Brand;

import java.util.List;

public interface BrandMapper {
    public Brand queryBrandById(Long id);
    public List<Brand> queryBrandList();
    public void insertBrand(Brand Brand);
    public void updateBrand(Brand Brand);
    public void deleteById(Long id);
}
