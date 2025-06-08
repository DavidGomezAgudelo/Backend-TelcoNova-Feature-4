package com.Fabrica.TelcoNova.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.dto.CreateEventInput;
import com.Fabrica.TelcoNova.model.EventModel;
import com.Fabrica.TelcoNova.service.EventService;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

   @MutationMapping
public EventModel createEvent(@Argument CreateEventInput input) {
    LocalDateTime fecha = LocalDateTime.parse(input.getEventDate());
    return eventService.createEvent(input.getEventTypeId(), input.getDescription(), fecha);
}


    @QueryMapping 
    public List<EventModel> getEvents(){
        return eventService.getEvents();
    }
}
