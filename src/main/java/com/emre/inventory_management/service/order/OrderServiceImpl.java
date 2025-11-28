package com.emre.inventory_management.service.order;

import com.emre.inventory_management.dto.OrderDTO;
import com.emre.inventory_management.dto.request.OrderRequest;
import com.emre.inventory_management.event.OrderEventProducer;
import com.emre.inventory_management.mapper.OrderMapper;
import com.emre.inventory_management.model.order.Order;
import com.emre.inventory_management.model.order.OrderItem;
import com.emre.inventory_management.model.order.OrderStatus;
import com.emre.inventory_management.model.product.Product;
import com.emre.inventory_management.model.user.User;
import com.emre.inventory_management.repository.OrderRepository;
import com.emre.inventory_management.repository.ProductRepository;
import com.emre.inventory_management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderEventProducer orderEventProducer;

    @Transactional
    public void createOrder(OrderRequest request) {

        if (request.getUserId() == null) {
            throw new IllegalArgumentException("userId can not be null");
        }

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getProductIdQuantityMap() == null || request.getProductIdQuantityMap().isEmpty()) {
            throw new IllegalArgumentException("No product provided in order request");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : request.getProductIdQuantityMap().entrySet()) {

            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());

            order.getItems().add(orderItem);

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        orderEventProducer.sendOrderEvent(order);
    }

    @Override
    public List<OrderDTO> getOrders(User user) {
        return orderRepository.findAllByUser(user).stream().map(orderMapper::toDTO).toList();
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(orderMapper::toDTO).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}


