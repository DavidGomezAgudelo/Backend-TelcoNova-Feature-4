package com.Fabrica.TelcoNova.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.model.EventModel;
import com.Fabrica.TelcoNova.service.EventService;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @MutationMapping
    public EventModel createEvent(
        @Argument Long eventTypeId,
        @Argument String description,
        @Argument String eventDate
    ) {
        LocalDateTime fecha = LocalDateTime.parse(eventDate);
        return eventService.createEvent(eventTypeId, description, fecha);
    }

    @QueryMapping 
    public List<EventModel> getEvents(){
        return eventService.getEvents();
    }
}
