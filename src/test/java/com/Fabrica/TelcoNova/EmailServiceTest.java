package com.Fabrica.TelcoNova;

import com.Fabrica.TelcoNova.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        String to = "david.gomez.agudelo9@gmail.com"; // Cambia esto a tu correo real
        String subject = "Prueba de envío";
        String body = "Hola, este es un correo de prueba desde Spring Boot.";

        emailService.sendEmail(to, subject, body);
        System.out.println("Correo enviado correctamente");
    }
}
