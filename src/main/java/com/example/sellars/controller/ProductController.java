package com.example.sellars.controller;

import com.example.sellars.models.Product;
import com.example.sellars.models.User;
import com.example.sellars.service.ProductService;
import com.example.sellars.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService service;


    @GetMapping(value = "/static/{filename}")
    public @ResponseBody byte[] getFile(@PathVariable("filename") String filename) throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("/static/" + filename);
        try {
            return in.readAllBytes();

        } catch (Exception e) {
            var error = new String("ERROR: css file (/css/" + filename + ") not found");
            return error.getBytes();
        }
    }

    @GetMapping("/")
    public String products(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                           @RequestParam(value = "sort", defaultValue = "2") Integer sortField,
                           @RequestParam(value = "category", defaultValue = "") String category,
                           @RequestParam(value = "title", defaultValue = "") String title,
                           Principal principal, Model model) {
        int size = productService.getPagesSize(limit, category, title);
        model.addAttribute("products", productService.findAllByA(offset, limit, sortField, category, title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("offset", offset);
        model.addAttribute("pageSize", size);
        model.addAttribute("pages", productService.getPagesList(size));
        model.addAttribute("title", title);
        return "products";
    }


    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("productUser", product.getUser());
        User user = principal == null ? null : service.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/my_room";
    }


    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}