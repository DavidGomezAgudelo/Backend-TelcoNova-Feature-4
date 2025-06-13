package com.Fabrica.TelcoNova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fabrica.TelcoNova.model.NotificationModel;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {

}
