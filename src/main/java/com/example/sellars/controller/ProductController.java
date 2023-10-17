package com.example.sellars.controller;

import com.example.sellars.model.Product;
import com.example.sellars.model.User;
import com.example.sellars.service.feign.FeignClientService;
import com.example.sellars.service.product.ProductService;
import com.example.sellars.service.rabbit.RabbitServiceImpl;
import com.example.sellars.service.user.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService service;
    private final FeignClientService client;
    private final RabbitServiceImpl rabbitService;


    @GetMapping(value = "/static/{filename}")
    public @ResponseBody byte[] getFile(@PathVariable("filename") String filename) {
        try (InputStream in = getClass().getResourceAsStream("/static/" + filename)) {

            return Objects.requireNonNull(in).readAllBytes();

        } catch (IOException e) {
            log.error("Ошибка в чтении файла: getFile(), " + this.getClass().getName());
            String error = "ERROR: css file (/css/" + filename + ") not found";
            return error.getBytes();
        }
    }


    @ApiResponse(content = {@Content(mediaType = "text/html")})
    @GetMapping("/")
    public String products(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                           @RequestParam(value = "sort", defaultValue = "2") Integer sortField,
                           @RequestParam(value = "category", defaultValue = "") String category,
                           @RequestParam(value = "title", defaultValue = "") String title,
                           Principal principal, Model model) {
        log.info("Запрос на получение главной страницы с пагинацией");
        int size = productService.getPagesSize(limit, category, title);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        model.addAttribute("products",
                productService.findAllByAttribute(offset, limit, sortField, category, title));
        model.addAttribute("user", service.getUserByEmail(principal.getName()));
        model.addAttribute("offset", offset);
        model.addAttribute("pageSize", size);
        model.addAttribute("pages", productService.getPagesList(size));
        model.addAttribute("sort", sortField);
        model.addAttribute("category", category);
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
        log.info("Запрос на получение товаров из определенного города");
        int size = productService.getPagesSize(limit, category, title);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        model.addAttribute("products",
                productService.findByCity(city, offset, limit, sortField, category, title));
        model.addAttribute("user", service.getUserByEmail(principal.getName()));
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
        log.info("Запрос на получение информации о товаре - " + id);
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
        log.info("Запрос на добавление в избранное товара - " + id + " пользователем - " + principal.getName());
        productService.likeProduct(principal, id);
        return "redirect:/product/{id}";
    }

    @PostMapping("/product/{id}/likeFromJS")
    public void likeProdFromJS(@PathVariable Long id, Principal principal) {
        log.info("Запрос на добавление в избранное товара - ");
        productService.likeProduct(principal, id);
    }

    @DeleteMapping("/product/{id}/deleteFromJS")
    public void deleteProdFromJS(@PathVariable Long id, Principal principal) {
        log.info("Запрос на добавление в избранное товара - ");
        productService.dislikeProduct(principal, id);
    }
}