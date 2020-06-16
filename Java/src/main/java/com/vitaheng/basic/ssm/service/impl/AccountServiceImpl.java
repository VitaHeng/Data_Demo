package com.vitaheng.basic.ssm.service.impl;

import com.vitaheng.basic.ssm.mapper.AccountMapper;
import com.vitaheng.basic.ssm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Transactional
    public void transfer(String outAccount, String inAccount, double money) {
        accountMapper.out(outAccount,money);
//        int i = 1/0;
        accountMapper.in(inAccount,money);
    }
}
