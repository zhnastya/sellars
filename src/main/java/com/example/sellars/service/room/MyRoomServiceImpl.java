package com.example.sellars.service.room;

import com.example.sellars.exeption.NotFoundException;
import com.example.sellars.model.Image;
import com.example.sellars.model.Product;
import com.example.sellars.model.User;
import com.example.sellars.repository.ProductRepository;
import com.example.sellars.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Qualifier("MyRoomServiceImpl")
@RequiredArgsConstructor
public class MyRoomServiceImpl implements MyRoomService {

    private final UserServiceImpl service;
    private final ProductRepository repository;


    private Image toImageEntity(MultipartFile file) throws IOException {
        Image imageForProduct = new Image();
        imageForProduct.setName(file.getName());
        imageForProduct.setOriginalFileName(file.getOriginalFilename());
        imageForProduct.setContentType(file.getContentType());
        imageForProduct.setSize(file.getSize());
        imageForProduct.setBytes(file.getBytes());
        return imageForProduct;
    }


    @SneakyThrows
    @Override
    public void updateUser(Principal principal, User user, MultipartFile file) {
        User user1 = getUserByPrincipal(principal);
        if (file != null && file.getSize() != 0) {
            Image image = toImageEntity(file);
            user1.setAvatar(image);
        }
        if (!service.updateUser(user)) {
            throw new NotFoundException("Ошибка в обновлении пользователя");
        }
    }


    @SneakyThrows
    @Override
    public void changeAvatar(Principal principal, MultipartFile file) {
        User user = getUserByPrincipal(principal);
        if (file.getSize() != 0) {
            user.setAvatar(toImageEntity(file));
        }
        service.updateUser(user);
    }


    @Override
    public Product getByIdProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Продукт не найден"));
    }


    @Override
    public void deleteUser(Principal principal) {
        if (!service.deleteUser(principal)) {
            throw new NotFoundException("Ошибка в удалении пользователя");
        }
    }


    @SneakyThrows
    @Override
    public void updateProduct(Long id, Product product, List<MultipartFile> files) {
        if (!files.isEmpty()) {
            List<Image> images = new ArrayList<>();
            for (MultipartFile file : files) {
                images.add(toImageEntity(file));
            }
            product.setImages(images);
        }
        repository.save(product);
    }


    @Override
    public List<Product> getMyProduct(Principal principal) {
        User user = getUserByPrincipal(principal);
        return repository.findAllByUser(user);
    }


    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }


    @Override
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return service.getUserByEmail(principal.getName());
    }
}
