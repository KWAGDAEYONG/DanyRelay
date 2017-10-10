package com.example.relay.config;

import com.example.relay.model.User;
import com.example.relay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;
import javax.servlet.http.HttpSession;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    private final Filter googleSignUpFilter;

    private HttpSession httpSession;


    public SecurityConfig(Filter googleSignUpFilter, HttpSession httpSession){
        this.googleSignUpFilter = googleSignUpFilter;
        this.httpSession = httpSession;
    }




    @Override
    public void configure(WebSecurity webSecurity)throws Exception{
        webSecurity.ignoring().antMatchers("/h2-console/**","/signUpByGoogle","/signInByGoogle");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/login","/signUp","/signUpByFacebook","/signUpByNaver","/setNickName","/signUpFinish").permitAll()
                    .anyRequest().authenticated()
                    .and()

                .formLogin()
                    .successHandler(successHandler())
                    .loginPage("/login")
                    .loginProcessingUrl("/loginProcessing")
                    .failureUrl("/login?error=true")
                    .and()

                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .and()
                .csrf().disable()
                .addFilterBefore(googleSignUpFilter, BasicAuthenticationFilter.class)
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(username);
                httpSession.setAttribute("loginUser", user);
                return user;
            }
        });
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new CustomSuccessHandler("/");
    }

}
