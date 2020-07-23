package com.vitaheng.service;

import com.vitaheng.mapper.SpecificationMapper;
import com.vitaheng.mapper.SpecificationOptionMapper;
import com.vitaheng.pojo.CombinSpecification;
import com.vitaheng.pojo.Specification;
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

    @Override
    public CombinSpecification queryListBySpecId(Long id) {
        Specification specification = specificationMapper.querySpecificationById(id);
        List<SpecificationOption> specificationOptions = specificationOptionMapper.queryListBySpecId(id);

        CombinSpecification combinSpecification = new CombinSpecification();
        combinSpecification.setSpecification(specification);
        combinSpecification.setSpecificationOptions(specificationOptions);
        return combinSpecification;
    }

    @Override
    public void updateSpecification(CombinSpecification combinSpecification) {
        specificationMapper.updateSpecification(combinSpecification.getSpecification());
        specificationOptionMapper.deleteById(combinSpecification.getSpecification().getId());
        List<SpecificationOption> specificationOptions = combinSpecification.getSpecificationOptions();
        for (SpecificationOption specificationOption : specificationOptions) {
            specificationOption.setSpecId(combinSpecification.getSpecification().getId());
            specificationOptionMapper.insertSpecificationOption(specificationOption);
        }
    }
}
