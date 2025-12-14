package com.emre.inventory_management.event;

import com.emre.inventory_management.model.order.Order;
import com.emre.inventory_management.model.product.Product;
import com.emre.inventory_management.repository.ProductRepository;
import com.emre.inventory_management.service.email.EmailServiceImpl;
import com.emre.inventory_management.service.email.EmailTemplateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderEventListener {

    private final ProductRepository productRepository;
    private final EmailServiceImpl emailService;
    private final ObjectMapper objectMapper;
    private final EmailTemplateServiceImpl emailTemplateService;

    @Autowired
    public OrderEventListener(ProductRepository productRepository, EmailServiceImpl emailService, ObjectMapper objectMapper, EmailTemplateServiceImpl emailTemplateService) {
        this.productRepository = productRepository;
        this.emailService = emailService;
        this.objectMapper = objectMapper;
        this.emailTemplateService = emailTemplateService;
    }

    @Transactional
    @KafkaListener(topics = "order", groupId = "order-service-group")
    public void listenOrder(String orderJson) {
        try {
            Order order = objectMapper.readValue(orderJson, Order.class);

            order.getItems().forEach(item -> {
                Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not found with id " + item.getProduct().getId()));

                long newStock = product.getStock() - item.getQuantity();
                if (newStock < 0) {
                    throw new IllegalStateException("Not enough stock for product " + product.getId());
                }

                product.setStock(newStock);
                productRepository.save(product);
            });

            System.out.println("Stock updated for order: " + order.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "order-mail", groupId = "order-mail-service-group")
    public void listenOrderMail(String orderJson) {
        try {
            Order order = objectMapper.readValue(orderJson, Order.class);

            emailService.sendOrderMail(order.getUser().getEmail(), emailTemplateService.buildOrderEmail(order.getUser().getFirstName(), order.getItems()));

            System.out.println("Received order mail event: " + orderJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: Simulate order progressing by updating order status as time passes and update the user simultaneously
}
