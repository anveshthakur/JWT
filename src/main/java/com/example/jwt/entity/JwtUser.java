package com.example.jwt.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class JwtUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String mobile;
    private boolean isOrganization;
    private String deviceToken;
    private Date createdAt;
    private Date updatedAt;

    public JwtUser(String email, String password, String mobile, boolean isOrganization, String deviceToken, Date createdAt, Date updatedAt) {
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.isOrganization = isOrganization;
        this.deviceToken = deviceToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
