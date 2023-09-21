package com.example.sellars.controller;

import com.example.sellars.models.Product;
import com.example.sellars.service.ProductService;
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

    @GetMapping(value="/static/{filename}")
    public @ResponseBody byte[] getFile(@PathVariable("filename") String filename) throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("/static/" + filename);
        try {
            return in.readAllBytes();

        } catch (Exception e){
            var error = new String("ERROR: css file (/css/" + filename + ") not found");
            return error.getBytes();
        }
    }

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title,
                           Principal principal, Model model) {
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("products", productService.listProducts(title));
        return "products";
    }


    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("user", product.getUser());
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