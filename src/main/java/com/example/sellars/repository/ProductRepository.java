package com.example.sellars.repository;

import com.example.sellars.model.Product;
import com.example.sellars.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByTitle(String title);

    List<Product> findAllByCategory(String category);

    List<Product> findAllByUser(User user);

    Optional<Product> findById(Long id);

    List<Product> findAllByCategoryAndTitle(String category, String title);

    Page<Product> findByTitleContaining(String companyName, Pageable paging);

    Page<Product> findByCityContainingAndTitleContaining(String city, String title, Pageable paging);

    Page<Product> findByCityContainingAndTitleContainingAndCategoryContaining(String city,
                                                                              String title,
                                                                              String category,
                                                                              Pageable paging);

    Page<Product> findAllByCityContaining(String city, Pageable paging);

    Page<Product> findByCategoryContaining(String category, Pageable paging);

    Page<Product> findByCategoryAndTitleContaining(String category, String title, Pageable pageable);
}