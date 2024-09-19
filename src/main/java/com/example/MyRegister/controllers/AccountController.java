package com.example.MyRegister.controllers;

import com.example.MyRegister.model.AppUser;
import com.example.MyRegister.model.RegisterDTO;
import com.example.MyRegister.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class AccountController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/register")
    public String register(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute(registerDTO);
        model.addAttribute("success", false);

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterDTO registerDTO, BindingResult bindingResult, Model model) {
        if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            bindingResult.addError(new FieldError("registerDTO", "ConfirmPassword", "Passwords do not match"));
        }

        AppUser appUser = appUserRepository.findByEmail(registerDTO.getEmail());
        if(appUser != null) {
            bindingResult.addError(new FieldError("registerDTO", "Email", "This email is already in use"));
        }

        if(bindingResult.hasErrors()) {
            return "register";
        }

        try{
            var bCryptEncoder = new BCryptPasswordEncoder();

            AppUser newUser = new AppUser();
            newUser.setEmail(registerDTO.getEmail());
            newUser.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
            newUser.setFirstName(registerDTO.getFirstName());
            newUser.setLastName(registerDTO.getLastName());
            newUser.setAddress(registerDTO.getAddress());
            newUser.setPhone(registerDTO.getPhone());
            newUser.setRole("client");
            newUser.setCreatedAt(new Date());

            appUserRepository.save(newUser);

            model.addAttribute("registerDTO", new RegisterDTO());
            model.addAttribute("success", true);

        }catch(Exception e) {
            bindingResult.addError(new FieldError("registerDTO", "firstName", e.getMessage()));
        }
        return "register";
    }
}
