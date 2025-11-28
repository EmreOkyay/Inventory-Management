package com.emre.inventory_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDTO {
    private Long id;
    private ProductDTO product;
    private int quantity;
    private BigDecimal price;
}
