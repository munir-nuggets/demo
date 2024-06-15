package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class controller {
    @GetMapping("/users/view")
    public String getAllUsers() {
        return "users/showAll";
    }
    
}
