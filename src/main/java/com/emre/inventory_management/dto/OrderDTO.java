package com.emre.inventory_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private UserDTO user;
    private String status;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> items;
}
