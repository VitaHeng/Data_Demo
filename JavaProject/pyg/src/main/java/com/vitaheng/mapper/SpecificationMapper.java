package com.vitaheng.mapper;


import com.vitaheng.pojo.Specification;

import java.util.List;

public interface SpecificationMapper {
    public Specification querySpecificationById(Long id);
    public List<Specification> querySpecificationList();
    public void insertSpecification(Specification Specification);
    public void updateSpecification(Specification Specification);
    public void deleteById(Long id);
}
