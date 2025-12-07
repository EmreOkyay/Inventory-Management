package com.emre.inventory_management.controller;

import com.emre.inventory_management.dto.OrderDTO;
import com.emre.inventory_management.model.user.User;
import com.emre.inventory_management.repository.UserRepository;
import com.emre.inventory_management.dto.request.OrderRequest;
import com.emre.inventory_management.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;
    private final UserRepository userRepository;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrders(@RequestParam String email) {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<OrderDTO> orders = orderServiceImpl.getOrders(user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/id/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO order = orderServiceImpl.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest user) {
        orderServiceImpl.createOrder(user);
        return ResponseEntity.ok("Order successfully created");
    }

    // New IDE test comment
}
