package com.Fabrica.TelcoNova.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification_targets")
public class NotificationTargetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationModel notification;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupModel group;

    @ManyToOne
    @JoinColumn(name = "delivery_method_id")
    private DeliveryMethodModel deliveryMethod;

    @ManyToOne
    @JoinColumn(name = "delivery_status_id")
    private DeliveryStatusModel deliveryStatus;

    private LocalDateTime sentAt;


    public NotificationTargetModel(){

        }

        public NotificationTargetModel(Long id, NotificationModel notification, GroupModel group,
            DeliveryMethodModel deliveryMethod, DeliveryStatusModel deliveryStatus, LocalDateTime sentAt) {
        this.id = id;
        this.notification = notification;
        this.group = group;
        this.deliveryMethod = deliveryMethod;
        this.deliveryStatus = deliveryStatus;
        this.sentAt = sentAt;
    }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public NotificationModel getNotification() {
            return notification;
        }

        public void setNotification(NotificationModel notification) {
            this.notification = notification;
        }

        public GroupModel getGroup() {
            return group;
        }

        public void setGroup(GroupModel group) {
            this.group = group;
        }

        public DeliveryMethodModel getDeliveryMethod() {
            return deliveryMethod;
        }

        public void setDeliveryMethod(DeliveryMethodModel deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

        public DeliveryStatusModel getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(DeliveryStatusModel deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }

        public LocalDateTime getSentAt() {
            return sentAt;
        }

        public void setSentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
        }

    
    
}
