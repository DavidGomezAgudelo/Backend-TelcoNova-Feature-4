package com.Fabrica.TelcoNova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fabrica.TelcoNova.model.DeliveryStatusModel;
import com.Fabrica.TelcoNova.repository.DeliveryStatusRepository;

@Service
public class DeliveryStatusService {

    @Autowired private DeliveryStatusRepository deliveryStatusRepository;


    public List<DeliveryStatusModel> getDeliveryStatuses(){
        return deliveryStatusRepository.findAll();
    }
}
