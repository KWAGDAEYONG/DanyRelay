package com.example.relay.controller;

import com.example.relay.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(Model model, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("loginUser");
        model.addAttribute("user",user);
        return "/index";
    }

    @GetMapping("/security")
    public String security(){
        return "/security";
    }
}
