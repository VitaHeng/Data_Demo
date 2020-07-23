package com.vitaheng.controller;

import com.vitaheng.pojo.CombinSpecification;
import com.vitaheng.pojo.PygResult;
import com.vitaheng.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
