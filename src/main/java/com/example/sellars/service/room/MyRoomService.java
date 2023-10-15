package com.example.sellars.service.room;

import com.example.sellars.model.Product;
import com.example.sellars.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface MyRoomService {
    void updateUser(Principal principal, User user, MultipartFile file);

    void changeAvatar(Principal principal, MultipartFile file);

    Product getByIdProduct(Long id);

    void deleteUser(Principal principal);

    void updateProduct(Long id, Product product, List<MultipartFile> files);

    List<Product> getMyProduct(Principal principal);

    void deleteProduct(Long id);

    User getUserByPrincipal(Principal principal);
}
