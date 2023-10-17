package com.example.sellars.controller;

import com.example.sellars.model.Comment;
import com.example.sellars.model.User;
import com.example.sellars.service.feign.FeignClientService;
import com.example.sellars.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FeignClientService client;


    @GetMapping("/{userId}")
    public String userInfo(@Valid @PathVariable("userId") User user, Model model) {
        log.info("Get запрос на подробную информацию о пользователе - " + user.getEmail());
        List<Comment> comments = client.getAll(user.getId());
        for (Comment comment : comments) {
            User user1 = userService.getUserById(comment.getAuthorId());
            comment.setAuthor(user1);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        model.addAttribute("date", user.getDateOfCreated().format(formatter));
        model.addAttribute("formatter", formatter);
        model.addAttribute("rating", client.getUserRate(user.getId()));
        model.addAttribute("comments", comments);
        return "user-info";
    }

    @PostMapping("/{userId}/comment")
    public String saveComm(@Valid @PathVariable("userId") User user,
                           Comment comment, Principal principal) {
        log.info("Запрос на публикацию комментария пользователю - " + user.getEmail());
        User user1 = userService.getUserByEmail(principal.getName());
        client.save(user.getId(), user1.getId(), comment);
        return "redirect:/user/{userId}";
    }
}