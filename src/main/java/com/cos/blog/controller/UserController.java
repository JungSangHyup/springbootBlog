package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/auth/joinform")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginform")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/user/updateform")
    public String updateForm(){
        return "user/updateForm";
    }
}
