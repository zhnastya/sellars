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
import java.util.*;


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

    public void saveProduct(Principal principal, Product product, List<MultipartFile> files) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        if (!files.isEmpty()) {
            List<Image> images = new ArrayList<>();
            for (MultipartFile file : files) {
                Image image = toImageEntity(file);
                images.add(image);
                product.addImageToProduct(image);
            }
            images.get(0).setPreviewImage(true);
            Product productFromDb = productRepository.save(product);
            productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        }
        log.info("Saving new Product. Title: {}; Author: {}", product.getTitle(), product.getUser().getName());
        productRepository.save(product);
    }

    private Product addManyProd(Principal principal, List<MultipartFile> files) throws IOException {
        Product product1 = new Product();
        List<String> category = List.of("Одежда", "Техника", "Товары для дома", "Товары для детей",
                "Товары для животных", "Авто", "Красота и здоровье", "Недвижимость", "Хобби и отдых");
        List<String> cities = List.of("Москва", "Санкт - Петербург", "Самара", "Воронеж", "Казань");
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            Image image = toImageEntity(file);
            images.add(image);
            product1.addImageToProduct(image);
        }
        images.get(0).setPreviewImage(true);
        Random random = new Random();
        String randomName = category.get(random.nextInt(category.size()));
        product1.setTitle(randomName + " " + cities.get(random.nextInt(cities.size())));
        product1.setDescription("Товар предназначенный для - " + randomName);
        product1.setPrice(random.nextInt(30000));
        product1.setCity(cities.get(random.nextInt(cities.size())));
        product1.setCategory(randomName);
        product1.setUser(getUserByPrincipal(principal));
        return product1;
    }

    public void addMany(Principal principal, List<MultipartFile> files) throws IOException {
        for (int i = 0; i < 100; i++) {
            Product product = addManyProd(principal, files);
            Product productFromDb = productRepository.save(product);
            productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
            productRepository.save(product);
        }
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

    public List<Product> findAllByAttribute(Integer offset, Integer limit,
                                            Integer sortField, String category, String title) {
        String sorting;
        Sort.Direction sort;

        if (sortField == 0) {
            sorting = "price";
            sort = Sort.Direction.ASC;
        } else if (sortField == 1) {
            sorting = "price";
            sort = Sort.Direction.DESC;
        } else if (sortField == 2) {
            sorting = "dateOfCreated";
            sort = Sort.Direction.DESC;
        } else {
            sorting = "dateOfCreated";
            sort = Sort.Direction.ASC;
        }
        return getByCategoryAndTitle(offset, limit, sorting, sort, category, title).getContent();
    }

    public List<Product> findByCity(String city, Integer offset, Integer limit,
                                            Integer sortField, String category, String title) {
        String sorting;
        Sort.Direction sort;

        if (sortField == 0) {
            sorting = "price";
            sort = Sort.Direction.ASC;
        } else if (sortField == 1) {
            sorting = "price";
            sort = Sort.Direction.DESC;
        } else if (sortField == 2) {
            sorting = "dateOfCreated";
            sort = Sort.Direction.DESC;
        } else {
            sorting = "dateOfCreated";
            sort = Sort.Direction.ASC;
        }
        return getByCity(city, offset, limit, sorting, sort, category, title);
    }

    private Page<Product> getByCategoryAndTitle(Integer offset, Integer limit,
                                                String sorting, Sort.Direction sort,
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


    public int getPagesSize(Integer limit, String category, String title) {
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

    private List<Product> getByCity(String city, Integer offset, Integer limit,
                                   String sorting, Sort.Direction sort,
                                   String category, String title){
        if (category.isEmpty() && !title.isEmpty()) {
            return productRepository.findByCityContainingAndTitleContaining(title, title,
                    PageRequest.of(offset, limit, Sort.by(sort, sorting))).getContent();
        } else if (!category.isEmpty() && title.isEmpty()) {
            return productRepository.findByCategoryContaining(category,
                    PageRequest.of(offset, limit, Sort.by(sort, sorting))).getContent();
        } else if (!title.isEmpty() && !category.isEmpty()) {
            return productRepository.findByCityContainingAndTitleContainingAndCategoryContaining(city,title, category,
                    PageRequest.of(offset, limit, Sort.by(sort, sorting))).getContent();
        } else {
            return productRepository.findAllByCityContaining(city, PageRequest.of(offset, limit, Sort.by(sort, sorting))).getContent();
        }
    }

    public List<Integer> getPagesList(int size) {
        List<Integer> pages = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            pages.add(i);
        }
        return pages;
    }

    public void likeProduct(Principal principal, Long prodId) {
        User user = getUserByPrincipal(principal);
        Product product = getProductById(prodId);
        Set<Product> products = new HashSet<>(user.getLikesProd() == null ? Collections.emptyList() : user.getLikesProd());
        products.add(product);
        user.setLikesProd(products);
        repository.save(user);
    }

    public void dislikeProduct(Principal principal, Long prodId) {
        User user = getUserByPrincipal(principal);
        Product product = getProductById(prodId);
        Set<Product> products = new HashSet<>(user.getLikesProd() == null ? Collections.emptyList() : user.getLikesProd());
        products.remove(product);
        user.setLikesProd(products);
        repository.save(user);
    }

    public Set<Product> getLikesProduct (Principal principal){
        User user = getUserByPrincipal(principal);
        return user.getLikesProd();
    }
}