package com.vitaheng.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vitaheng.mapper.BrandMapper;
import com.vitaheng.pojo.Brand;
import com.vitaheng.pojo.PageResult;
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

    @Override
    public PageResult queryPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page brandList = (Page) brandMapper.queryBrandList();
        return new PageResult(brandList.getTotal(),brandList.getResult());

    }

    @Override
    public void insertBrand(Brand brand) {
        brandMapper.insertBrand(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateBrand(brand);
    }

    @Override
    public void deleteBrandById(Long[] ids) {
        for (Long id : ids) {
            brandMapper.deleteById(id);
        }
    }
}
