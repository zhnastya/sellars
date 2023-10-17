package com.example.sellars.controller;

import com.example.sellars.exeption.NotFoundException;
import com.example.sellars.model.User;
import com.example.sellars.service.email.DefaultEmailService;
import com.example.sellars.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@RequiredArgsConstructor
public class SecurityController {
    private final UserService userService;
    private final DefaultEmailService service;


    @GetMapping("/login")
    public String login() {
        log.info("Get запрос на авторизацию");
        return "login";
    }


    @PostMapping("/login")
    public String log() {
        log.info("Запрос на авторизацию");
        return "redirect:/";
    }


    @GetMapping("/registration")
    public String registration() {
        log.info("Get запрос на регистрацию");
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(User user) {
        log.info("Запрос на регистрацию пользователя - " + user.getEmail());
        if (!userService.createUser(user))
            throw new NotFoundException("Ошибка регистрации пользователя");
        return "redirect:/login";
    }


    @GetMapping("/login/reset")
    public String getResetPage() {
        log.info("Get запрос на восстановление пароля");
        return "reset";
    }


    @PostMapping("/login/reset")
    public String postResetPage(@RequestParam String email) {
        log.info("Запрос на восстановление пароля пользователя - " + email);
        User user = userService.getUserByEmail(email);
        service.sendSimpleEmail(email, "Восстановление пароля",
                "Привет дружище! Вот твой пароль - " + user.getPassword());

        return "redirect:/login";
    }
}
