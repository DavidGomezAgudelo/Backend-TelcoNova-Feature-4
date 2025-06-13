package com.Fabrica.TelcoNova.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Fabrica.TelcoNova.config.RabbitMQConfig;
import com.Fabrica.TelcoNova.dto.SendNotificationInput;
import com.Fabrica.TelcoNova.model.AlertModel;
import com.Fabrica.TelcoNova.model.DeliveryMethodModel;
import com.Fabrica.TelcoNova.model.DeliveryStatusModel;
import com.Fabrica.TelcoNova.model.GroupModel;
import com.Fabrica.TelcoNova.model.NotificationModel;
import com.Fabrica.TelcoNova.model.NotificationTargetModel;
import com.Fabrica.TelcoNova.repository.AlertRepository;
import com.Fabrica.TelcoNova.repository.DeliveryMethodRepository;
import com.Fabrica.TelcoNova.repository.DeliveryStatusRepository;
import com.Fabrica.TelcoNova.repository.GroupRepository;
import com.Fabrica.TelcoNova.repository.NotificationRepository;
import com.Fabrica.TelcoNova.repository.NotificationTargetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired private NotificationRepository notificationRepository;
    @Autowired private AlertRepository alertRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private DeliveryMethodRepository deliveryMethodRepository;
    @Autowired private DeliveryStatusRepository deliveryStatusRepository;
    @Autowired private NotificationTargetRepository notificationTargetRepository;
    

    @Transactional
    public NotificationModel sendNotification(SendNotificationInput input) {
        AlertModel alert = alertRepository.findById(input.getAlertId())
                .orElseThrow(() -> new IllegalArgumentException("Alerta no encontrada con ID: " + input.getAlertId()));
        
        // Asumimos que tienes un método para buscar el método de entrega "EMAIL"
        // Podrías cachearlo para mejor rendimiento.
        DeliveryMethodModel emailMethod = deliveryMethodRepository.findByName("EMAIL")
                .orElseThrow(() -> new IllegalStateException("Método de entrega 'EMAIL' no encontrado."));

        DeliveryStatusModel initialStatus = deliveryStatusRepository.findByName("PENDING");
          
        NotificationModel notification = new NotificationModel();
        notification.setAlert(alert);
        notification.setScheduledDate(input.getScheduledDate() != null ? LocalDateTime.parse(input.getScheduledDate()) : LocalDateTime.now());
        notification.setCreatedAt(LocalDateTime.now());
        NotificationModel savedNotification = notificationRepository.save(notification);

        List<Long> groupIds = input.getGroupIds();
        List<Long> deliveryMethodIds = input.getDeliveryMethodIds();

        if (groupIds.size() != deliveryMethodIds.size()) {
            throw new IllegalArgumentException("Para el emparejamiento, las listas de grupos y métodos de entrega deben tener el mismo tamaño.");
        }

        List<NotificationTargetModel> targets = new ArrayList<>();
        for (int i = 0; i < groupIds.size(); i++) {
            Long groupId = groupIds.get(i);
            Long deliveryMethodId = deliveryMethodIds.get(i);
            
            GroupModel group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado con ID: " + groupId));
            
            DeliveryMethodModel deliveryMethod = deliveryMethodRepository.findById(deliveryMethodId)
                    .orElseThrow(() -> new IllegalArgumentException("Método de entrega no encontrado con ID: " + deliveryMethodId));

            NotificationTargetModel target = new NotificationTargetModel();
            target.setNotification(savedNotification);
            target.setGroup(group);
            target.setDeliveryMethod(deliveryMethod);
            target.setDeliveryStatus(initialStatus);
            
            targets.add(target);
        }
        
        // Guardamos todos los targets en la base de datos
        List<NotificationTargetModel> savedTargets = notificationTargetRepository.saveAll(targets);

        // Ahora, publicamos los mensajes en la cola
        for (NotificationTargetModel target : savedTargets) {
            // Solo encolamos si el método de entrega es EMAIL
            if (target.getDeliveryMethod().getId().equals(emailMethod.getId())) {
                System.out.println("Encolando notificación de email para target ID: " + target.getId());
                // Enviamos solo el ID. El consumidor se encargará de buscar los detalles.
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, target.getId());
            }
        }

        return savedNotification;
    }

    public List<NotificationModel> getNotifications() {
        return notificationRepository.findAll();
    }

    public NotificationModel getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
    }
}