package com.emre.inventory_management.service.email;

public interface EmailService {
    void sendOrderMail(String to, String email);
    void sendUserMail(String to, String email);
}
