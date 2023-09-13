package com.example.sellars.service;

import com.example.sellars.models.Product;
import com.example.sellars.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<Product> getProductsList(String title) {
        if (title!=null) repository.findByTitle(title);
        return repository.findAll();
    }

    public void saveProduct(Product product) {
        log.info("SAVE new {}", product);
        repository.save(product);
    }

    public void removeProduct(int id) {
        repository.deleteById(id);
    }

    public Product getById(int id) {
        return repository.findById(id).orElse(null);
    }
}
