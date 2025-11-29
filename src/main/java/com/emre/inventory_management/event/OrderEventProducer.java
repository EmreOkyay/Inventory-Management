package com.emre.inventory_management.event;

import com.emre.inventory_management.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendOrderEvent(Order order) {
        try {
            String eventJson = objectMapper.writeValueAsString(order);

            kafkaTemplate.send("order", eventJson);

            System.out.println("Sent order event: " + eventJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendOrderMailEvent(Order order) {
        try {
            String eventJson = objectMapper.writeValueAsString(order);

            kafkaTemplate.send("order-mail", eventJson);

            System.out.println("Sent order mail event: " + eventJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
