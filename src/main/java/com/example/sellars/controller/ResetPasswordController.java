package com.example.sellars.controller;

import com.example.sellars.service.DefaultEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ResetPasswordController {
    private final DefaultEmailService service;

    @GetMapping("/login/reset")
    public String getResetPage(){
        return "reset";
    }

    @PostMapping("/login/reset")
    public String postResetPage(@RequestParam String email){
        String token = UUID.randomUUID().toString();
        service.sendSimpleEmail(email, "Simple email", "Привет свин!");
        return "redirect:/login";
    }
}
