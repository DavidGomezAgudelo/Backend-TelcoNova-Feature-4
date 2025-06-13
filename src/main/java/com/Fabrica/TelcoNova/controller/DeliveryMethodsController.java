package com.Fabrica.TelcoNova.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.model.DeliveryMethodModel;
import com.Fabrica.TelcoNova.service.AuthorizationService;
import com.Fabrica.TelcoNova.service.DeliveryMethodService;

import graphql.GraphQLContext;

@Controller
public class DeliveryMethodsController {

    private final DeliveryMethodService deliveryMethodService;
    private final AuthorizationService authorizationService; 

    public DeliveryMethodsController(DeliveryMethodService deliveryMethodService,AuthorizationService authorizationService) {
        this.deliveryMethodService = deliveryMethodService;
        this.authorizationService= authorizationService;
    }

    @QueryMapping 
    public List<DeliveryMethodModel> getDeliveryMethods(GraphQLContext context){
        authorizationService.getAuthenticatedUser(context);
        return deliveryMethodService.getDeliveryMethods();
    }
}
