package com.example.sellars.service;

import com.example.sellars.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private List<Product> productsList = new ArrayList<>();
    private int id = 0;

    {
        productsList.add(
                new Product(++id, "PlayStation", "new Game Controller",
                        50000, "Moscow", "Anastasia"));
        productsList.add(
                new Product(++id, "PlayStation 4", "Game Controller",
                        60000, "Moscow", "Alex"));

    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void saveProduct(Product product) {
        product.setId(++id);
        productsList.add(product);
    }

    public void removeProduct(int id) {
        productsList.removeIf(s->s.getId()==id);
    }
}
