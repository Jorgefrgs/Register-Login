package com.example.MyRegister.model;

import jakarta.persistence.*;

import java.util.Date;
import lombok.*;

@Entity
@Table(name = "users")
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;

    @Column(unique = true,nullable = false)
    private String email;

    private String phone;
    private String password;
    private String address;
    private String role;
    private Date createdAt;
}
