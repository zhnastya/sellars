package com.example.sellars.service.product;

import com.example.sellars.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface ProductService {

    void saveProduct(Principal principal, Product product, List<MultipartFile> files);

    void addMany(Principal principal, List<MultipartFile> files);

    void deleteProduct(Long id);

    Product getProductById(Long id);

    List<Product> findAllByAttribute(Integer offset, Integer limit,
                                     Integer sortField, String category, String title);

    List<Product> findByCity(String city, Integer offset, Integer limit,
                             Integer sortField, String category, String title);

    List<Integer> getPagesList(int size);

    int getPagesSize(Integer limit, String category, String title);

    void likeProduct(Principal principal, Long prodId);

    void dislikeProduct(Principal principal, Long prodId);

    Set<Product> getLikesProduct(Principal principal);
}
