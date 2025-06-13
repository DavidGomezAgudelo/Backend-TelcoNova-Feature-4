package com.Fabrica.TelcoNova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fabrica.TelcoNova.model.EventTypeModel;
import com.Fabrica.TelcoNova.repository.EventTypeRepository;


@Service
public class EventTypeService {


    @Autowired
    private EventTypeRepository eventTypeRepository;

    public List<EventTypeModel> getEventsTypes(){
        return eventTypeRepository.findAll();
    }
    
}
