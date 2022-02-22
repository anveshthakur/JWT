package com.example.jwt.controller;

import com.example.jwt.dto.ApiResponse;
import com.example.jwt.dto.JwtResponse;
import com.example.jwt.dto.LoginRequest;
import com.example.jwt.dto.SignUpRequest;
import com.example.jwt.entity.JwtUser;
import com.example.jwt.repository.JwtRepository;
import com.example.jwt.service.MyUserDetailsService;
import com.example.jwt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtRepository jwtRepository;

    //Sign up
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest){
        if(jwtRepository.findUserByEmail(signUpRequest.getEmail()) != null){
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        Date tt = today.getTime();
        JwtUser jwtUser = new JwtUser();
        jwtUser.setEmail(signUpRequest.getEmail()); //set email
        jwtUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); //password
        jwtUser.setMobile(signUpRequest.getMobile());
        jwtUser.setOrganization(signUpRequest.getOrganisation());
        jwtUser.setCreatedAt(tt);
        jwtRepository.save(jwtUser);
        return ResponseEntity.ok(new ApiResponse(true, "User Registered Successfully"));
    }

    //Signin
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(true, "Successfully Logged in", jwt));
    }
}
