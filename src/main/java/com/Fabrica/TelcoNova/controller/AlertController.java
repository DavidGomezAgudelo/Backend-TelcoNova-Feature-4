package com.Fabrica.TelcoNova.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.dto.CreateAlertInput;
import com.Fabrica.TelcoNova.dto.UpdateAlertInput;
import com.Fabrica.TelcoNova.model.AlertModel;
import com.Fabrica.TelcoNova.service.AlertService;
import com.Fabrica.TelcoNova.service.AuthorizationService;

import graphql.GraphQLContext;

@Controller
public class AlertController {

    private final AlertService alertService;
    private final AuthorizationService authorizationService; 

    public AlertController(AlertService alertService, AuthorizationService authorizationService) {
        this.alertService = alertService;
        this.authorizationService = authorizationService; 
    }

    @MutationMapping
    public AlertModel createAlert(@Argument CreateAlertInput input, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return alertService.createAlert(input);
    }

    @MutationMapping
    public boolean updateAlert(@Argument Long id, @Argument UpdateAlertInput input, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return alertService.updateAlert(id, input);
    }

    @MutationMapping
    public boolean deleteAlert(@Argument Long id, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return alertService.deleteAlert(id);
    }

    
    @QueryMapping
    public List<AlertModel> getAlerts(GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return alertService.getAlerts();
    }

    @QueryMapping
    public AlertModel getAlertById(@Argument Long id, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return alertService.getAlertById(id);
    }
}
