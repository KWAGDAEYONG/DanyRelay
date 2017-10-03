package com.example.relay.controller;

import com.example.relay.config.GoogleUser;
import com.example.relay.model.User;
import com.example.relay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;


    @PostMapping("/addUser")
    public String addUser(User user){
        //이미 있는 이메일일 경우 처리
        user.setType("common");
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

    @GetMapping("/setNickName")
    public String setNickName(){
        return "/user/setNickName";
    }

    @GetMapping("/signInByGoogle")
    public String signInByGoogle(HttpSession httpSession, Model model){
        GoogleUser user = (GoogleUser)httpSession.getAttribute("signInUser");
        String email = user.getEmail();
        String password = "TEMPPASSWOD";
        model.addAttribute("email",email);
        model.addAttribute("password",password);
        return "/user/tempForLogin";
    }

    @PostMapping("/signUpFinish")
    public String signUpFinish(String nickname, HttpSession httpSession, Model model){
        GoogleUser user = (GoogleUser)httpSession.getAttribute("signUpUser");
        User signUpUser = new User();
        signUpUser.signIn(user.getName(), user.getEmail(), nickname);
        signUpUser.setType("google");
        userRepository.save(signUpUser);
        httpSession.removeAttribute("signUpUser");
        model.addAttribute("email",user.getEmail());
        model.addAttribute("password","TEMPPASSWOD");
        return "/user/tempForLogin";
    }

}
