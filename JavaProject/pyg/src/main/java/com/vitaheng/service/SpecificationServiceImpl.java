package com.vitaheng.service;

import com.vitaheng.mapper.SpecificationMapper;
import com.vitaheng.mapper.SpecificationOptionMapper;
import com.vitaheng.pojo.CombinSpecification;
import com.vitaheng.pojo.SpecificationOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public void insertSpecification(CombinSpecification combinSpecification) {
        specificationMapper.insertSpecification(combinSpecification.getSpecification());
        List<SpecificationOption> specificationOptions = combinSpecification.getSpecificationOptions();
        for (SpecificationOption specificationOption : specificationOptions) {
            specificationOption.setSpecId(combinSpecification.getSpecification().getId());
            specificationOptionMapper.insertSpecificationOption(specificationOption);
        }


    }
}
