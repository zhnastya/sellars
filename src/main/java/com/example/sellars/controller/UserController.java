package com.example.sellars.controller;

import com.example.sellars.models.User;
import com.example.sellars.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String log() {
        return "redirect:/?offset=0&&limit=5";
    }

    @GetMapping("/my_room")
    public String myRoom(Principal principal, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("products", user.getProducts());
        model.addAttribute("date", user.getDateOfCreated().format(formatter));
        model.addAttribute("user", user);
        model.addAttribute("image", user.getAvatar());
        return "myRoom";
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(User user) {
        userService.createUser(user);
        return "redirect:/login";
    }

    @PostMapping("/my_room")
    public String createAvatar(@RequestParam("file") MultipartFile file, Principal user) throws IOException {
        userService.addAvatar(user, file);
        return "redirect:/my_room";
    }


    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        model.addAttribute("date", user.getDateOfCreated().format(formatter));
        return "user-info";
    }
}