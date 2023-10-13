package com.example.sellars.controller;

import com.example.sellars.models.Product;
import com.example.sellars.models.User;
import com.example.sellars.rabbit.RabbitService;
import com.example.sellars.service.FeignClientImpl;
import com.example.sellars.service.ProductService;
import com.example.sellars.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.security.Principal;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService service;
    private final FeignClientImpl client;
    private final RabbitService rabbitService;


    @GetMapping(value = "/static/{filename}")
    public @ResponseBody byte[] getFile(@PathVariable("filename") String filename) {
        InputStream in = getClass()
                .getResourceAsStream("/static/" + filename);
        try {
            return in.readAllBytes();

        } catch (Exception e) {
            var error = new String("ERROR: css file (/css/" + filename + ") not found");
            return error.getBytes();
        }
    }

    @GetMapping()
    public String products(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                           @RequestParam(value = "sort", defaultValue = "2") Integer sortField,
                           @RequestParam(value = "category", defaultValue = "") String category,
                           @RequestParam(value = "title", defaultValue = "") String title,
                           Principal principal, Model model) {
        int size = productService.getPagesSize(limit, category, title);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        model.addAttribute("products",
                productService.findAllByAttribute(offset, limit, sortField, category, title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("offset", offset);
        model.addAttribute("pageSize", size);
        model.addAttribute("pages", productService.getPagesList(size));
        model.addAttribute("sort", sortField);
        model.addAttribute("category", title);
        model.addAttribute("title", title);
        model.addAttribute("favorite", productService.getLikesProduct(principal));
        model.addAttribute("formatter", formatter);
        return "products";
    }

    @GetMapping("/{city}")
    public String productsByCity(@PathVariable("city") String city,
                                 @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                           @RequestParam(value = "sort", defaultValue = "2") Integer sortField,
                           @RequestParam(value = "category", defaultValue = "") String category,
                           @RequestParam(value = "title", defaultValue = "") String title,
                           Principal principal, Model model) {
        int size = productService.getPagesSize(limit, category, title);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        model.addAttribute("products",
                productService.findByCity(city, offset, limit, sortField, category, title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("offset", offset);
        model.addAttribute("pageSize", size);
        model.addAttribute("pages", productService.getPagesList(size));
        model.addAttribute("sort", sortField);
        model.addAttribute("category", title);
        model.addAttribute("title", title);
        model.addAttribute("favorite", productService.getLikesProduct(principal));
        model.addAttribute("formatter", formatter);
        return "products";
    }


    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("productUser", product.getUser());
        model.addAttribute("rating", client.getUserRate(product.getUser().getId()));
        User user = principal == null ? null : service.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "product-info";
    }

    @GetMapping("/map")
    public String mapInfo() {
        return "map";
    }

    @GetMapping("/rabbit")
    public void rabbit() {
        rabbitService.createProviders();
        rabbitService.createDelivery();
    }

    @PostMapping("/product/{id}/like")
    public String likeProd(@PathVariable Long id, Principal principal) {
        productService.likeProduct(principal, id);
        return "redirect:/product/{id}";
    }

    @PostMapping("/product/{id}/likeFromJS")
    public void likeProdFromJS(@PathVariable Long id, Principal principal) {
        productService.likeProduct(principal, id);
    }

    @DeleteMapping("/product/{id}/deleteFromJS")
    public void deleteProdFromJS(@PathVariable Long id, Principal principal) {
        productService.dislikeProduct(principal, id);
    }
}