package com.example.sellars.controller;

import com.example.sellars.models.Product;
import com.example.sellars.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/")
    public String getProduct(Model model) {
        model.addAttribute("products", service.getProductsList());
        return "products";
    }

    @PostMapping("/product/create")
    public String createProduct(Product product) {
        service.saveProduct(product);
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String delete(@PathVariable int id) {
        service.removeProduct(id);
        return "redirect:/";
    }

    @GetMapping("/product/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("product", service.getById(id));
        return "product-info";
    }
}
