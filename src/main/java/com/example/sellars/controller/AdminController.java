package com.example.sellars.controller;

import com.example.sellars.model.User;
import com.example.sellars.model.enums.Role;
import com.example.sellars.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;


    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }


    @PostMapping("/admin/ban/user/{id}")
    public String userBlock(@PathVariable("id") Long id) {
        userService.blockUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/user/{user}")
    public String updateUser(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping("/admin/edit/user")
    public String editUser(@RequestParam("userId") User user,
                           @RequestParam("roles") Map<String, String> form) {
        userService.changeUserRole(user, form);
        return "redirect:/admin";
    }
}
