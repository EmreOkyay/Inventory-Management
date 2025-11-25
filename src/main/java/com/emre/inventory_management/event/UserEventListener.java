package com.emre.inventory_management.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventListener {
    @KafkaListener(topics = "demo", groupId = "user-service-group")
    public void listen(String message) {
        System.out.println("Received user event: " + message);
    }
}

