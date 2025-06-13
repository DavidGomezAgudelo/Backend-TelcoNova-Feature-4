package com.Fabrica.TelcoNova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fabrica.TelcoNova.model.DeliveryMethodModel;
import com.Fabrica.TelcoNova.repository.DeliveryMethodRepository;

@Service
public class DeliveryMethodService {

    @Autowired private DeliveryMethodRepository deliveryMethodRepository;


    public List<DeliveryMethodModel> getDeliveryMethods(){
        return deliveryMethodRepository.findAll();
    }
}
