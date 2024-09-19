package com.example.MyRegister.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class RegisterDTO {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    private String phone;
    private String address;

    @Size(min=6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;

    private String confirmPassword;
}
