package com.emre.inventory_management.mapper;

import com.emre.inventory_management.dto.OrderDTO;
import com.emre.inventory_management.model.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserMapper userMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUser(userMapper.toDTO(order.getUser()));
        dto.setStatus(order.getStatus().toString());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setItems(order.getItems()
                .stream()
                .map(orderItemMapper::toDTO)
                .toList());
        return dto;
    }
}

