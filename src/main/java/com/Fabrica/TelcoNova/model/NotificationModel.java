package com.Fabrica.TelcoNova.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alert_id")
    private AlertModel alert;

    private LocalDateTime scheduledDate;

  

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    private Set<NotificationTargetModel> targets = new HashSet<>();


    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public NotificationModel(){
        
    }

    public NotificationModel(Long id, AlertModel alert, LocalDateTime scheduledDate,
            Set<NotificationTargetModel> targets, LocalDateTime createdAt) {
        this.id = id;
        this.alert = alert;
        this.scheduledDate = scheduledDate;
        this.targets = targets;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlertModel getAlert() {
        return alert;
    }

    public void setAlert(AlertModel alert) {
        this.alert = alert;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Set<NotificationTargetModel> getTargets() {
        return targets;
    }

    public void setTargets(Set<NotificationTargetModel> targets) {
        this.targets = targets;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    
    
}