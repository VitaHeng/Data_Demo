package com.vitaheng.basic.ssm.mapper;

import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    public void out(@Param("name") String outAccount, @Param("money") double money);
    public void in(@Param("name") String inAccount,@Param("money") double money);
}
