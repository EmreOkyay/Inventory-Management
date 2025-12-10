package com.emre.inventory_management.event;

import com.emre.inventory_management.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class UserEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUserEvent(String event) {
        kafkaTemplate.send("user", event);
    }

    public void sendUserMailEvent(User user) {
        try {
            String eventJson = objectMapper.writeValueAsString(user);

            kafkaTemplate.send("user-mail", eventJson);

            System.out.println("Sent user mail event: " + eventJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
