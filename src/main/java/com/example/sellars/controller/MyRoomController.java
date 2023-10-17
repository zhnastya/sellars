package com.example.sellars.controller;

import com.example.sellars.model.Comment;
import com.example.sellars.model.Product;
import com.example.sellars.model.User;
import com.example.sellars.service.feign.FeignClientService;
import com.example.sellars.service.product.ProductService;
import com.example.sellars.service.room.MyRoomService;
import com.example.sellars.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;


@Controller
@Slf4j
@RequestMapping("/my-room")
@RequiredArgsConstructor
public class MyRoomController {
    private final ProductService productService;
    private final UserService userService;
    private final MyRoomService myRoomService;
    private final FeignClientService implTest;


    @GetMapping
    public String myRoom(Principal principal, Model model) {
        log.info("Get запрос личного кабинета пользователя - " + principal.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("products", user.getProducts());
        model.addAttribute("date", user.getDateOfCreated().format(formatter));
        model.addAttribute("user", user);
        model.addAttribute("image", user.getAvatar());
        return "myRoom";
    }


    @GetMapping("/products")
    public String getMyProducts(Principal principal, Model model) {
        log.info("Get запрос товаров пользователя - " + principal.getName());
        List<Product> products = myRoomService.getMyProduct(principal);
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("products", products);
        return "myProducts";
    }

    @GetMapping("/product/{id}")
    public String infoProduct(@PathVariable("id") Long id,
                              Principal principal, Model model) {
        log.info("Get запрос на информацию о товаре - " + id);
        Product product = myRoomService.getByIdProduct(id);
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "my-room_product";
    }

    @GetMapping("/create/product")
    public String createProduct(Principal principal, Model model) {
        log.info("Get запрос на создание товара пользователя - " + principal.getName());
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "create-product";
    }

    @PostMapping("/create/product")
    public String createProduct(@RequestParam("files") List<MultipartFile> files,
                                Product product, Principal principal) {
        log.info("Запрос на создание товара пользователя - " + principal.getName());
        productService.saveProduct(principal, product, files);
        return "redirect:/my-room";
    }


    @DeleteMapping("/delete/product/{id}")
    public String pageDeleteProduct(@PathVariable("id") Long id) {
        log.info("Запрос на удаление товара - " + id);
        myRoomService.deleteProduct(id);
        return "redirect:/my-products";
    }


    @GetMapping("/change/product/{id}")
    public String pageUpdateProduct(@PathVariable("id") Long id, Principal principal, Model model) {
        log.info("Get запрос на изменение товара - " + id);
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("product", productService.getProductById(id));
        return "update-product";
    }


    @PutMapping("/change/product/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @RequestParam(value = "files", required = false) List<MultipartFile> files,
                                @Valid Product product) {
        log.info("Запрос на изменение товара - " + id);
        myRoomService.updateProduct(id, product, files);
        return "redirect:/my-products";
    }

    @GetMapping("/like-products")
    public String getLikeProducts(Principal principal, Model model) {
        log.info("Get запрос избранных товаров пользователя - " + principal.getName());
        Set<Product> products = productService.getLikesProduct(principal);
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("products", products);
        return "likes-product";
    }

    @GetMapping("/change/user")
    public String pageUpdateUser(Principal principal, Model model) {
        log.info("Get запрос на изменение пользователя - " + principal.getName());
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "update-user";
    }


    @PutMapping("/change/user")
    public String updateUser(@RequestParam(value = "file", required = false) MultipartFile file,
                             @Valid User user, Principal principal){
        log.info("Запрос на изменение пользователя - " + principal.getName());
        myRoomService.updateUser(principal, user, file);
        return "redirect:/my-room";
    }

    @PostMapping("/add-avatar")
    public String createAvatar(@RequestParam("file") MultipartFile file, Principal user) {
        log.info("Запрос на добавление аватара пользователя - " + user.getName());
        userService.addAvatar(user, file);
        return "redirect:/my-room";
    }

    @PostMapping("/change/avatar")
    public String updateAvatar(@RequestParam(value = "file", required = false) MultipartFile file, Principal principal){
        log.info("Запрос на изменение аватара пользователя - " + principal.getName());
        myRoomService.changeAvatar(principal, file);
        return "redirect:/my-room";
    }


    @PostMapping("/delete/user")
    public String pageDeleteUser(Principal principal) {
        log.info("Запрос на удаление пользователя - " + principal.getName());
        myRoomService.deleteUser(principal);
        return "redirect:/";
    }


    @GetMapping("/comments")
    public String getMyComments(Principal principal, Model model) {
        log.info("Get запрос комментариев пользователя - " + principal.getName());
        User user = myRoomService.getUserByPrincipal(principal);
        List<Comment> comments = implTest.getMyComments(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        return "my-comments";
    }


    @PostMapping("/create/many")
    public String createMany(@RequestParam("files") List<MultipartFile> files, Principal principal){
        productService.addMany(principal, files);
        return "redirect:/my-room";
    }
}
