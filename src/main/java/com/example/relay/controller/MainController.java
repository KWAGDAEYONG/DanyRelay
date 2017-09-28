package com.example.relay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(){
        return "/index";
    }

    @GetMapping("/security")
    public String security(){
        return "/security";
    }
}
