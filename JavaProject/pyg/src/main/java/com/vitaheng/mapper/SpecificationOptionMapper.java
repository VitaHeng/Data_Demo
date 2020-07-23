package com.vitaheng.mapper;


import com.vitaheng.pojo.SpecificationOption;

import java.util.List;

public interface SpecificationOptionMapper {
    public SpecificationOption querySpecificationOptionById(Long id);
    public List<SpecificationOption> queryListBySpecId(Long specId);
    public List<SpecificationOption> querySpecificationOptionList();
    public void insertSpecificationOption(SpecificationOption specificationOption);
    public void updateSpecificationOption(SpecificationOption specificationOption);
    public void deleteById(Long id);
}
