package com.example.jwt.controller;

import com.example.jwt.entity.JwtUser;
import com.example.jwt.repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class AppController {
    @Autowired
    JwtRepository jwtRepository;

    @GetMapping("/checkUser")
    public String checkUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }

    @GetMapping("/getUser")
    public JwtUser accessUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        JwtUser jwtUser = jwtRepository.findUserByEmail(currentUserName);
        return jwtUser;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Nil here";
    }

}
