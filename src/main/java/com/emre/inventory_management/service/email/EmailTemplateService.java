package com.emre.inventory_management.service.email;

import com.emre.inventory_management.model.order.OrderItem;

import java.util.List;

public interface EmailTemplateService {
    String buildOrderEmail(String customerName, List<OrderItem> items);
    String buildUserEmail(String customerName);
}
