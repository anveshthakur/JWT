package com.example.jwt.repository;

import com.example.jwt.entity.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JwtRepository extends JpaRepository<JwtUser, Long> {
    List<JwtUser> findAll();
    JwtUser findUserByEmail(String email);
}

//username = email