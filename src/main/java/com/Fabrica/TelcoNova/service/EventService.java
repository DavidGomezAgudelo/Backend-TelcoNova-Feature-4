package com.Fabrica.TelcoNova.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fabrica.TelcoNova.model.EventModel;
import com.Fabrica.TelcoNova.model.EventTypeModel;
import com.Fabrica.TelcoNova.repository.EventRepository;
import com.Fabrica.TelcoNova.repository.EventTypeRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public EventModel createEvent(Long eventTypeId, String description, LocalDateTime eventDate) {
    EventTypeModel eventType = eventTypeRepository.findById(eventTypeId)
        .orElseThrow(() -> new RuntimeException("EventType not found with id: " + eventTypeId));

    EventModel event = new EventModel();
    event.setEventType(eventType);
    event.setDescription(description);
    event.setEventDate(eventDate);
    
    return eventRepository.save(event);
}



    public List<EventModel> getEvents() {
        return eventRepository.findAll();
    }


}
