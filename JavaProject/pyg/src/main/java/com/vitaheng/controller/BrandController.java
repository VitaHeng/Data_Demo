package com.vitaheng.controller;

import com.vitaheng.pojo.Brand;
import com.vitaheng.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @RequestMapping("queryBrandList")
    @ResponseBody
    public List<Brand> queryBrandList() {
        List<Brand> brandList = brandService.queryBrandList();
        return brandList;
    }


}
