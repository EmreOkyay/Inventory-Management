package com.emre.inventory_management.repository;

import com.emre.inventory_management.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name=:name")
    Optional<Product> getProductByName(String name);

    @Query("SELECT p FROM Product p WHERE p.category=:category")
    List<Product> getProductsByCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.isAvailable=true")
    List<Product> getProductsWithAvailableStock();
}
