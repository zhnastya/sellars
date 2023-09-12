package com.example.sellars.controller;

import com.example.sellars.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/")
    public String getProduct(Model model) {
        model.addAttribute("products", service.getProductsList());
        return "products";
    }
}
