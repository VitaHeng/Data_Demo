package com.vitaheng.controller;

import com.vitaheng.pojo.CombinSpecification;
import com.vitaheng.pojo.PygResult;
import com.vitaheng.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @RequestMapping("insertSpec")
    public PygResult insertSpecification(@RequestBody CombinSpecification combinSpecification) {
        try {
            specificationService.insertSpecification(combinSpecification);
            return new PygResult(true,"插入成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"插入失败");
        }
    }

    @RequestMapping("querySpec/{id}")
    public CombinSpecification querySpecificationById(@PathVariable("id") Long id) {
        return specificationService.queryListBySpecId(id);
    }

    @RequestMapping("updateSpec")
    public PygResult updateSpecification(CombinSpecification combinSpecification) {
        try {
            specificationService.updateSpecification(combinSpecification);
            return new PygResult(true,"更新成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"更新失败");
        }
    }



}
