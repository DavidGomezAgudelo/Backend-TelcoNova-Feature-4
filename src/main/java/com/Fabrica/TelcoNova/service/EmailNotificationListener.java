package com.Fabrica.TelcoNova.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.Fabrica.TelcoNova.config.RabbitMQConfig;
import com.Fabrica.TelcoNova.model.NotificationTargetModel;
import com.Fabrica.TelcoNova.model.UserGroupModel;
import com.Fabrica.TelcoNova.model.DeliveryStatusModel;
import com.Fabrica.TelcoNova.model.UserModel; // Asumo que tienes un UserModel en tu GroupModel
import com.Fabrica.TelcoNova.repository.NotificationTargetRepository;
import com.Fabrica.TelcoNova.repository.DeliveryStatusRepository;
import com.Fabrica.TelcoNova.service.EmailService;

@Component
public class EmailNotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationListener.class);

    @Autowired
    private NotificationTargetRepository targetRepository;

    @Autowired
    private DeliveryStatusRepository statusRepository;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    @Transactional
    public void handleEmailNotification(Long notificationTargetId) {
        logger.info("Mensaje recibido para el target ID: {}", notificationTargetId);

        NotificationTargetModel target = targetRepository.findById(notificationTargetId).orElse(null);
        if (target == null) {
            logger.error("No se encontró el NotificationTarget con ID: {}", notificationTargetId);
            return;
        }

        DeliveryStatusModel statusSent = statusRepository.findByName("SENT");
        DeliveryStatusModel statusFailed = statusRepository.findByName("FAILED");

        try {
            String subject = target.getNotification().getAlert().getTitle();
            String body = target.getNotification().getAlert().getDescription();

            if (target.getGroup().getUsers() == null || target.getGroup().getUsers().isEmpty()) {
                logger.warn("El grupo con ID {} no tiene usuarios asociados. Marcando como enviado.", target.getGroup().getId());
            } else {
                for (UserGroupModel userGroup : target.getGroup().getUsers()) {
                    UserModel user = userGroup.getUser();
                    if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
                        emailService.sendEmail(user.getEmail(), subject, body);
                    } else {
                        logger.warn("Usuario en grupo ID {} sin correo electrónico válido.", target.getGroup().getId());
                    }
                }
            }

            target.setDeliveryStatus(statusSent);
            targetRepository.save(target);

        } catch (Exception e) {
            logger.error("Fallo al procesar la notificación para el target ID: {}. Error: {}", notificationTargetId, e.getMessage());
            target.setDeliveryStatus(statusFailed);
            targetRepository.save(target);
        }
    }
}
