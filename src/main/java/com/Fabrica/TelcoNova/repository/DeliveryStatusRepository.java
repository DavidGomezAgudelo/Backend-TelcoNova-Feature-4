package com.Fabrica.TelcoNova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fabrica.TelcoNova.model.DeliveryStatusModel;



@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatusModel, Long> {

    DeliveryStatusModel findByName(String name);
}
