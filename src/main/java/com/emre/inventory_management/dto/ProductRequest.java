package com.emre.inventory_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Long stock;
    private Boolean isAvailable = true;
}
