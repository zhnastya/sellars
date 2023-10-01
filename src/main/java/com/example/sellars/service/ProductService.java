package com.example.sellars.service;

import com.example.sellars.models.Image;
import com.example.sellars.models.Product;
import com.example.sellars.models.User;
import com.example.sellars.repository.ProductRepository;
import com.example.sellars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository repository;


    public List<Product> listProducts(String title) {
        if (title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void saveProduct(Principal principal, Product product, MultipartFile file1,
                            MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image imageForProduct1;
        Image imageForProduct2;
        Image imageForProduct3;
        if (file1.getSize() != 0) {
            imageForProduct1 = toImageEntity(file1);
            imageForProduct1.setPreviewImage(true);
            product.addImageToProduct(imageForProduct1);
        }
        if (file2.getSize() != 0) {
            imageForProduct2 = toImageEntity(file2);
            product.addImageToProduct(imageForProduct2);
        }
        if (file3.getSize() != 0) {
            imageForProduct3 = toImageEntity(file3);
            product.addImageToProduct(imageForProduct3);
        }
        log.info("Saving new Product. Title: {}; Author: {}", product.getTitle(), product.getUser().getName());
        Product productFromDb = productRepository.save(product);
        if (!product.getImages().isEmpty()) {
            productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        }
        productRepository.save(product);
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
        return repository.findByEmail(principal.getName());
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findAllByA(Integer offset, Integer limit,
                                    Integer sortField, String category, String title){
        String sorting;
        Sort.Direction sort;

        if (sortField==0) {
            sorting = "price";
            sort = Sort.Direction.ASC;
        } else if (sortField==1) {
            sorting = "price";
            sort = Sort.Direction.DESC;
        } else if (sortField == 2) {
            sorting = "dateOfCreated";
            sort = Sort.Direction.DESC;
        }else{
            sorting = "dateOfCreated";
            sort = Sort.Direction.ASC;
        }
        return getByCategoryAndTitle(offset, limit, sorting, sort, category, title).getContent();
    }

    private Page<Product> getByCategoryAndTitle(Integer offset, Integer limit,
                                                String sorting,Sort.Direction sort,
                                                String category, String title) {
        if (category.isEmpty() && !title.isEmpty()) {
            return productRepository.findByTitleContaining(title,
                    PageRequest.of(offset, limit, Sort.by(sort, sorting)));
        } else if (!category.isEmpty() && title.isEmpty()) {
            return productRepository.findByCategoryContaining(category,
                    PageRequest.of(offset, limit, Sort.by(sort, sorting)));
        } else if (!title.isEmpty() && !category.isEmpty()) {
            return productRepository.findByCategoryAndTitleContaining(category, title,
                    PageRequest.of(offset, limit, Sort.by(sort, sorting)));
        } else {
            return productRepository.findAll(PageRequest.of(offset, limit, Sort.by(sort, sorting)));
        }
    }


    public int getPagesSize (Integer limit, String category, String title) {
        List<Product> allProducts;
        if (!category.isEmpty() && title.isEmpty()) {
            allProducts = productRepository.findAllByCategory(category);
        } else if (category.isEmpty() && !title.isEmpty()) {
            allProducts = productRepository.findByTitle(title);
        } else if (!category.isEmpty() && !title.isEmpty()) {
            allProducts = productRepository.findAllByCategoryAndTitle(category, title);
        } else {
            allProducts = productRepository.findAll();
        }
        return allProducts.size() % limit > 0
                ? allProducts.size() / limit + 1
                : allProducts.size() / limit;
    }

    public List<Integer> getPagesList(int size){
        List<Integer> pages = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            pages.add(i);
        }
        return pages;
    }
}