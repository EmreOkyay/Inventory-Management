package com.emre.inventory_management.service.email;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void testSendOrderMail_SuccessfullySendsMail() {
        // given
        String to = "test@test.com";
        String body = "Order confirmed";

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // when
        emailService.sendOrderMail(to, body);

        // then
        verify(mailSender).createMimeMessage();
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void testSendUserMail_SuccessfullySendsMail() {
        // given
        String to = "test@test.com";
        String body = "User registered";

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // when
        emailService.sendOrderMail(to, body);

        // then
        verify(mailSender).createMimeMessage();
        verify(mailSender).send(mimeMessage);
    }
}