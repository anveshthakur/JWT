package com.example.jwt.service;

import com.example.jwt.entity.JwtUser;
import com.example.jwt.repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    JwtRepository jwtRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        JwtUser jwtUser = jwtRepository.findUserByEmail(email);
        if(jwtUser == null) throw new UsernameNotFoundException("email not found" + email);
        return new User(jwtUser.getEmail(), jwtUser.getPassword(), new ArrayList<>());
    }
}
