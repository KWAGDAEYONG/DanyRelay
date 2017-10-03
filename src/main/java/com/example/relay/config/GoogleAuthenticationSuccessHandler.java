package com.example.relay.config;

import com.example.relay.model.User;
import com.example.relay.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Component
public class GoogleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private HttpSession httpSession;
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    public GoogleAuthenticationSuccessHandler(HttpSession httpSession, ObjectMapper objectMapper){
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication
    )throws IOException, ServletException{
        GoogleUser googleUser = getGoogleUser(authentication);
        List<User> users = userRepository.findByType("google");
        boolean isMember = false;
        for(int i = 0; i<users.size(); i++){
            if(users.get(i).getEmail().equals(googleUser.getEmail())){
                isMember = true;
            }
        }
        if(isMember){
            httpSession.setAttribute("signInUser",googleUser);
            response.sendRedirect("/signInByGoogle");
        }else {
            httpSession.setAttribute("signUpUser", googleUser);
            response.sendRedirect("/setNickName");
        }
    }

    private GoogleUser getGoogleUser(Authentication authentication){
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        return objectMapper.convertValue(oAuth2Authentication.getUserAuthentication().getDetails(), GoogleUser.class);
    }

}
