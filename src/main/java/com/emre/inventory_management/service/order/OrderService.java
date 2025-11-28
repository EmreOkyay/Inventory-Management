package com.emre.inventory_management.service.order;

import com.emre.inventory_management.dto.OrderDTO;
import com.emre.inventory_management.model.user.User;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getOrders(User user);
    OrderDTO getOrderById(Long orderId);
}
