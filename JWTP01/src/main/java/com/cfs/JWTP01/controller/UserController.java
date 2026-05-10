package com.cfs.JWTP01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/tests")
    public String test(){
        return "test users";
    }

    @GetMapping("/info")
    public String info(){
        return "last login at 9pm";
    }
}
