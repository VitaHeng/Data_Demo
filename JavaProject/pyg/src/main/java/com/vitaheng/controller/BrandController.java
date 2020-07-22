package com.vitaheng.controller;

import com.vitaheng.pojo.Brand;
import com.vitaheng.pojo.PageResult;
import com.vitaheng.pojo.PygResult;
import com.vitaheng.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @RequestMapping("queryBrandList")
    public List<Brand> queryBrandList() {
        List<Brand> brandList = brandService.queryBrandList();
        return brandList;
    }

    @RequestMapping("queryPage/{pageNum}/{pageSize}")
    public PageResult queryPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        PageResult pageResult = brandService.queryPage(pageNum, pageSize);
        return pageResult;
    }

    @RequestMapping("insertBrand")
    public PygResult insertBrand(@RequestBody Brand brand) {
        try {
            brandService.insertBrand(brand);
            return new PygResult(true,"插入成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"插入失败");
        }
    }

    @RequestMapping("updateBrand")
    public PygResult updateBrand(@RequestBody Brand brand) {
        try {
            brandService.updateBrand(brand);
            return new PygResult(true,"更新成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"更新失败");
        }
    }

    @RequestMapping("deleteBrandById")
    public PygResult deleteBrandById(Long[] ids) {
        try {
            brandService.deleteBrandById(ids);
            return new PygResult(true,"删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"删除失败");
        }
    }
}
