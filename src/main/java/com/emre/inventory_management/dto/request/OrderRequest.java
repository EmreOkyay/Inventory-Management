package com.emre.inventory_management.dto.request;

import lombok.Data;

import java.util.Map;

@Data
public class OrderRequest {
    private Long userId;
    private Map<Long, Integer> productIdQuantityMap;
}
