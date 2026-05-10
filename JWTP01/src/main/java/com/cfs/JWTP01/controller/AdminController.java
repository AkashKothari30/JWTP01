package com.cfs.JWTP01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashBoard")
    public String adminDashBoard(){
        return "admin DashBoard";
    }

    @GetMapping("/users")
    public String getUsers(){
        return "aman,praveen,sumit";
    }
}
