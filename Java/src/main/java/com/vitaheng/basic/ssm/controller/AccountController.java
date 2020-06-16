package com.vitaheng.basic.ssm.controller;

import com.vitaheng.basic.ssm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("transfer")
    public ModelAndView transfer(@RequestParam("outAccount") String outAccount,@RequestParam("inAccount") String inAccount,@RequestParam("money") double money) {
        accountService.transfer(outAccount,inAccount,money);
        ModelAndView mv = new ModelAndView("success");
        return mv.addObject("msg","转账成功"+outAccount+","+inAccount+","+money);
    }
}
