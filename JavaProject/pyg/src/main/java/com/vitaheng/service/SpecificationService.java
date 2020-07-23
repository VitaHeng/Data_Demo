package com.vitaheng.service;

import com.vitaheng.pojo.CombinSpecification;

public interface SpecificationService {
    public void insertSpecification(CombinSpecification combinSpecification);
    public CombinSpecification queryListBySpecId(Long id);
    public void updateSpecification(CombinSpecification combinSpecification);

}
