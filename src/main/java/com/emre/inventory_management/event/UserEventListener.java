package com.emre.inventory_management.event;

import com.emre.inventory_management.model.user.User;
import com.emre.inventory_management.service.email.EmailServiceImpl;
import com.emre.inventory_management.service.email.EmailTemplateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class UserEventListener {

    private final ObjectMapper objectMapper;
    private final EmailServiceImpl emailService;
    private final EmailTemplateServiceImpl emailTemplateService;

    @Autowired
    public UserEventListener(ObjectMapper objectMapper, EmailServiceImpl emailService, EmailTemplateServiceImpl emailTemplateService) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.emailTemplateService = emailTemplateService;
    }

    @KafkaListener(topics = "user", groupId = "user-service-group")
    public void listen(String message) {
        System.out.println("Received user event: " + message);
    }

    @KafkaListener(topics = "user-mail", groupId = "user-mail-service-group")
    public void listenUserMail(String orderJson) {
        try {
            User user = objectMapper.readValue(orderJson, User.class);

            emailService.sendUserMail(user.getEmail(), emailTemplateService.buildUserEmail(user.getFirstName()));

            System.out.println("Received user mail event: " + orderJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
