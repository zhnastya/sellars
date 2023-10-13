package com.example.sellars.service;

import com.example.sellars.models.Image;
import com.example.sellars.models.Product;
import com.example.sellars.models.User;
import com.example.sellars.repository.ProductRepository;
import com.example.sellars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyRoomService {

    private final UserRepository userRepository;
    private final ProductRepository repository;

    public void updateUser(Principal principal, User user, MultipartFile file) throws IOException {
        User user1 = getUserByPrincipal(principal);
        if (file!=null&&file.getSize() != 0) {
            Image image = toImageEntity(file);
            user1.setAvatar(image);
        }
        if (user != null) {
            user1.setName(user.getName().isEmpty()?user1.getName(): user.getName());
            user1.setEmail(user.getEmail().isEmpty()? user1.getEmail() : user.getEmail());
            user1.setPhoneNumber(user.getPhoneNumber().isEmpty()? user1.getPhoneNumber() : user.getPhoneNumber());
        }
        userRepository.save(user1);
    }

    public void changeAvatar(Principal principal, MultipartFile file) throws IOException {
        User user = getUserByPrincipal(principal);
        if (file.getSize()!=0){
            user.setAvatar(toImageEntity(file));
        }
        userRepository.save(user);
    }

    public Product getByIdProduct(Principal principal, Long id){
        User user = getUserByPrincipal(principal);
        return repository.findAllByUser(user)
                .stream()
                .filter(s->s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteUser(Principal principal){
        userRepository.delete(getUserByPrincipal(principal));
    }

    public void updateProduct(Long id, Principal principal, Product product, List<MultipartFile> files) throws IOException {
        Product product1 = repository.findById(id).orElseThrow();
        product1.setTitle(product.getTitle());
        product1.setDescription(product.getDescription());
        product1.setCity(product.getCity());
        product1.setPrice(product.getPrice());
        product1.setPreviewImageId(product.getPreviewImageId());
        product1.setCategory(product.getCategory());
        if (!files.isEmpty()) {
            List<Image> images = new ArrayList<>();
            for (MultipartFile file : files) {
                images.add(toImageEntity(file));
            }
            product1.setImages(images);
        }
        repository.save(product1);
    }

    public List<Product> getMyProduct(Principal principal){
        User user = getUserByPrincipal(principal);
        return repository.findAllByUser(user);
    }

    public void deleteProduct(Long id){
        repository.deleteById(id);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image imageForProduct = new Image();
        imageForProduct.setName(file.getName());
        imageForProduct.setOriginalFileName(file.getOriginalFilename());
        imageForProduct.setContentType(file.getContentType());
        imageForProduct.setSize(file.getSize());
        imageForProduct.setBytes(file.getBytes());
        return imageForProduct;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }


}
