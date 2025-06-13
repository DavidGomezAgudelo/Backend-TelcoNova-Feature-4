package com.Fabrica.TelcoNova.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.model.EventTypeModel;
import com.Fabrica.TelcoNova.service.AuthorizationService;
import com.Fabrica.TelcoNova.service.EventTypeService;

import graphql.GraphQLContext;


@Controller
public class EventTypeController {
    

    private final EventTypeService eventTypeService;
    private final AuthorizationService authorizationService; 
    

    public EventTypeController(EventTypeService eventTypeService,AuthorizationService authorizationService) {
        this.eventTypeService = eventTypeService;
        this.authorizationService=authorizationService;
    }


    @QueryMapping 
    public List<EventTypeModel> getEventTypes(GraphQLContext context){
        authorizationService.validateAdmin(context);
        return eventTypeService.getEventsTypes();
    }
}
