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

    @PostMapping("/loginProcessing")
    public String loginProcessing(String email, String password){
        System.out.println("ㅇㅋ");
        User user = userRepository.findByEmail(email);

        if(user.getPassword().equals(password)){

        }
        return "/index";
    }
}
