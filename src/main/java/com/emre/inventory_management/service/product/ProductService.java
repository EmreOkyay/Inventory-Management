package com.emre.inventory_management.service.product;

import com.emre.inventory_management.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductDTO> getProductById(Long id);
    Optional<ProductDTO> getProductByName(String name);
    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> getProductsWithAvailableStock ();
}
