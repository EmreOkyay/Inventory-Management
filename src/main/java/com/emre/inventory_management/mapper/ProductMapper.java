package com.emre.inventory_management.mapper;

import com.emre.inventory_management.dto.ProductDTO;
import com.emre.inventory_management.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setStock(product.getStock());
        dto.setIsAvailable(product.getIsAvailable());
        return dto;
    }
}

