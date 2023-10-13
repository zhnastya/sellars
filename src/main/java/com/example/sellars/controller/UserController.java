package com.example.sellars.controller;

import com.example.sellars.models.Comment;
import com.example.sellars.models.User;
import com.example.sellars.service.FeignClientImpl;
import com.example.sellars.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FeignClientImpl client;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String log() {
        return "redirect:/?offset=0&&limit=5";
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


    @GetMapping("/user/{userId}")
    public String userInfo(@PathVariable("userId") User user, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        model.addAttribute("date", user.getDateOfCreated().format(formatter));
        return "user-info";
    }

    @GetMapping("/user/{userId}/comment")
    public String commentPage(@PathVariable("userId") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("comments", client.getAll(user.getId()));
        model.addAttribute("rating", client.getUserRate(user.getId()));
        return "comment";
    }

    @PostMapping("/user/{userId}/comment/save")
    public String saveComm(@PathVariable("userId") User user,
                           Comment comment, Principal principal) {
        User user1 = userService.getUserByEmail(principal.getName());
        client.save(user.getId(), user1.getId(), comment);
        return "redirect:/user/{userId}";
    }
}