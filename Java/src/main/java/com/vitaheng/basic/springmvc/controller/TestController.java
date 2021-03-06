package com.vitaheng.basic.springmvc.controller;

import com.vitaheng.basic.mybatis.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("mvc")
public class TestController {

   @RequestMapping("res_user")
    public ModelAndView res_user() {
       ModelAndView mv = new ModelAndView("user-list");
       ArrayList<User> users = new ArrayList<>();
       for (int i=0;i<4;i++) {
           User user = new User();
           user.setId(Long.valueOf(i));
           user.setName("zhang"+i);
           users.add(user);
       }
       return mv.addObject("users",users);
   }

    @RequestMapping("res_json")
    @ResponseBody
    public List<User> resposeJsonfunc() {
        ArrayList<User> users = new ArrayList<>();
        for (int i=0;i<4;i++) {
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setName("zhang"+i);
            users.add(user);
        }
        return users;
    }

    @RequestMapping("req_json")
    @ResponseStatus(HttpStatus.OK)
    public void req_Json(@RequestBody() List<User> users) {
        for (User user : users) {
            System.out.println(user.getId());
            System.out.println(user.getName());
            System.out.println(user.getUserName());
        }
    }

    @RequestMapping("path_variable/{id}")
    public ModelAndView path_variable(@PathVariable("id")Long id) {
        ModelAndView path_vari = new ModelAndView("path_vari");
        return path_vari.addObject("pathVari","占位符映射:"+id);
    }


}
