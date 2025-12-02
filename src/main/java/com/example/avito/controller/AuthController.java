package com.example.avito.controller;

import com.example.avito.dto.RegistrationDto;
import com.example.avito.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // страница логина
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // страница регистрации
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "auth/register";
    }

    // обработка формы регистрации
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("registrationDto") RegistrationDto form) {
        userService.registerNewUser(form);
        return "redirect:/auth/login?registered";
    }
}