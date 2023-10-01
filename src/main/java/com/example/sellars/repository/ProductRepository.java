package com.example.sellars.repository;

import com.example.sellars.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByTitle(String title);
    List<Product> findAllByCategory(String category);
    List<Product> findAllByCategoryAndTitle(String category, String title);
    Page<Product> findByTitleContaining(String companyName, Pageable paging);
    Page<Product> findByCategoryContaining(String category, Pageable paging);
    Page<Product> findByCategoryAndTitleContaining(String category, String title, Pageable pageable);
}