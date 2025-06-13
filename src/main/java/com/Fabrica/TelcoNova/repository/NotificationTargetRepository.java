package com.Fabrica.TelcoNova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fabrica.TelcoNova.model.NotificationTargetModel;

@Repository
public interface NotificationTargetRepository extends JpaRepository<NotificationTargetModel,Long >{
    

}
