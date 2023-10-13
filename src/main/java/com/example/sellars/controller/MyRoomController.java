package com.example.sellars.controller;

import com.example.sellars.models.Comment;
import com.example.sellars.models.Product;
import com.example.sellars.models.User;
import com.example.sellars.service.FeignClientImpl;
import com.example.sellars.service.MyRoomService;
import com.example.sellars.service.ProductService;
import com.example.sellars.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MyRoomController {
    private final ProductService productService;
    private final UserService service;
    private final MyRoomService myRoomService;
    private final FeignClientImpl implTest;

    @GetMapping("/my-room")
    public String myRoom(Principal principal, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        User user = service.getUserByEmail(principal.getName());
        model.addAttribute("products", user.getProducts());
        model.addAttribute("date", user.getDateOfCreated().format(formatter));
        model.addAttribute("user", user);
        model.addAttribute("image", user.getAvatar());
        return "myRoom";
    }

    @GetMapping("/my-room/my-products")
    public String getMyProducts(Principal principal, Model model){
        List<Product> products = myRoomService.getMyProduct(principal);
        model.addAttribute("user", service.getUserByEmail(principal.getName()));
        model.addAttribute("products", products);
        return "myProducts";
    }

    @GetMapping("/my-room/comments")
    public String getMyComments(Principal principal, Model model){
        User user = myRoomService.getUserByPrincipal(principal);
        List<Comment> comments = implTest.getMyComments(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        return "my-comments";
    }

    @GetMapping("/my-room/like-products")
    public String getLikeProducts(Principal principal, Model model){
        Set<Product> products = productService.getLikesProduct(principal);
        model.addAttribute("user", service.getUserByEmail(principal.getName()));
        model.addAttribute("products", products);
        return "likes-product";
    }


    @GetMapping("/my-room/change/user")
    public String pageUpdateUser(Principal principal, Model model){
        model.addAttribute("user", service.getUserByEmail(principal.getName()));
        return "update-user";
    }

    @PostMapping("/my-room/change/user")
    public String updateUser(@RequestParam(value = "file", required = false) MultipartFile file,
                             User user, Principal principal) throws IOException {
        myRoomService.updateUser(principal, user, file);
        return "redirect:/my-room";
    }

    @PostMapping("/my-room/change/avatar")
    public String updateAvatar(@RequestParam(value = "file", required = false) MultipartFile file,
                               Principal principal) throws IOException {
        myRoomService.changeAvatar(principal, file);
        return "redirect:/my-room";
    }

    @PostMapping("/my-room/delete/user")
    public String pageDeleteUser(Principal principal){
        myRoomService.deleteUser(principal);
        return "redirect:/";
    }

    @PostMapping("/my-room/create/product")
    public String createProduct(@RequestParam("files") List<MultipartFile> files,
                                Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, files);
        return "redirect:/my-room";
    }

    @PostMapping("/my-room/delete/product/{id}")
    public String pageDeleteProduct(@PathVariable("id") Long id){
        myRoomService.deleteProduct(id);
        return "redirect:/my-products";
    }

    @GetMapping("/my-room/change/product/{id}")
    public String pageUpdateProduct(@PathVariable("id") Long id, Principal principal, Model model){
        model.addAttribute("user", service.getUserByEmail(principal.getName()));
        model.addAttribute("product", productService.getProductById(id));
        return "update-product";
    }

    @PostMapping("/my-room/change/product/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @RequestParam(value = "files", required = false) List<MultipartFile> files,
                                Product product, Principal principal) throws IOException {
        myRoomService.updateProduct(id, principal, product, files);
        return "redirect:/my-products";
    }

    @PostMapping("/my-room/create/many")
    public String createMany(@RequestParam("files") List<MultipartFile> files,
                             Principal principal) throws IOException {
        productService.addMany(principal, files);
        return "redirect:/my-room";
    }
    @GetMapping("/my-room/create/product")
    public String createProduct(Principal principal, Model model) {
        model.addAttribute("user", service.getUserByEmail(principal.getName()));
        return "create-product";
    }

    @GetMapping("/my-room/product/{id}/info")
    public String infoProduct(@PathVariable("id") Long id,
                              Principal principal, Model model) {
        Product product = myRoomService.getByIdProduct(principal, id);
        model.addAttribute("user", service.getUserByEmail(principal.getName()));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "my-room_product";
    }

    @PostMapping("/my-room/add-avatar")
    public String createAvatar(@RequestParam("file") MultipartFile file, Principal user) throws IOException {
        service.addAvatar(user, file);
        return "redirect:/my-room";
    }
}
