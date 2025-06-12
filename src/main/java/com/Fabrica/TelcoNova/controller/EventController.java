package com.Fabrica.TelcoNova.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.dto.CreateEventInput;
import com.Fabrica.TelcoNova.model.EventModel;
import com.Fabrica.TelcoNova.service.AuthorizationService;
import com.Fabrica.TelcoNova.service.EventService;

import graphql.GraphQLContext;

@Controller
public class EventController {

    private final EventService eventService;
    private final AuthorizationService authorizationService; 

    public EventController(EventService eventService,AuthorizationService authorizationService) {
        this.eventService = eventService;
        this.authorizationService= authorizationService;
    }

   @MutationMapping
public EventModel createEvent(@Argument CreateEventInput input, GraphQLContext context) {
    authorizationService.validateAdmin(context);
    LocalDateTime fecha = LocalDateTime.parse(input.getEventDate());
    return eventService.createEvent(input.getEventTypeId(), input.getDescription(), fecha);
}


    @QueryMapping 
    public List<EventModel> getEvents(GraphQLContext context){
        authorizationService.validateAdmin(context);
        return eventService.getEvents();
    }
}
