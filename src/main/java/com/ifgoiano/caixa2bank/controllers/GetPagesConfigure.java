package com.ifgoiano.caixa2bank.controllers;

import com.ifgoiano.caixa2bank.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class GetPagesConfigure implements WebMvcConfigurer {
    @Autowired
    private EmailService emailService;

    @GetMapping("/user/register")
    public String getRegisterPage() {
        return "register";
    }

    @GetMapping("/user/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error";
    }

    @GetMapping({"/", "/home"})
    public String getHomePage() {
        return "index";
    }

}
