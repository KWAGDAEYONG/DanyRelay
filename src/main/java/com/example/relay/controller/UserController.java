package com.example.relay.controller;

import com.example.relay.model.User;
import com.example.relay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/addUser")
    public String addUser(User user){
        userRepository.save(user);
        return "/index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        String refferer = request.getHeader("Referer");
        if(refferer!=null&&!refferer.endsWith("login")) {
            request.getSession().setAttribute("prevPage", refferer);
        }
        return "/user/login";
    }

    @GetMapping("/signUp")
    public String signUp(){
        return "/user/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(User user){
        userRepository.save(user);
        return "/index";
    }

}
