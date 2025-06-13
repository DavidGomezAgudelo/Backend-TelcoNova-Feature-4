package com.Fabrica.TelcoNova.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.model.DeliveryStatusModel;
import com.Fabrica.TelcoNova.service.AuthorizationService;
import com.Fabrica.TelcoNova.service.DeliveryStatusService;

import graphql.GraphQLContext;



@Controller
public class DeliveryStatusController {

    private final DeliveryStatusService deliveryStatusService;
    private final AuthorizationService authorizationService; 

    public DeliveryStatusController(DeliveryStatusService deliveryStatusService,AuthorizationService authorizationService) {
        this.deliveryStatusService = deliveryStatusService;
        this.authorizationService= authorizationService;
    }

    @QueryMapping 
    public List<DeliveryStatusModel> getDeliveryStatuses(GraphQLContext context){
        authorizationService.getAuthenticatedUser(context);
        return deliveryStatusService.getDeliveryStatuses();
    }
}
