package com.example.relay.controller;

import com.example.relay.model.User;
import com.example.relay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/addUser")
    public String addUser(User user){
        userRepository.save(user);
        return null;
    }
}
