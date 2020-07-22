package com.cn.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {

    /**
     * 这个控制器是需要我们获取授权Token后使用Token才可以访问到的
     */
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello() {
        return "Secure Hello!";
    }

    public void SecuritySourceCode(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
