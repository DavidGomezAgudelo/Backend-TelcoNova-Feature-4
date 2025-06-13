package com.Fabrica.TelcoNova.controller;

import com.Fabrica.TelcoNova.dto.SendNotificationInput;
import com.Fabrica.TelcoNova.model.NotificationModel;
import com.Fabrica.TelcoNova.service.AuthorizationService;
import com.Fabrica.TelcoNova.service.NotificationService;
import graphql.GraphQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NotificationController {

    @Autowired private NotificationService notificationService;
    @Autowired private AuthorizationService authorizationService;

    @MutationMapping
    public NotificationModel sendNotification(@Argument SendNotificationInput input, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return notificationService.sendNotification(input);
    }
    
    @QueryMapping
    public List<NotificationModel> getNotifications( GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return notificationService.getNotifications();
    }
    
    @QueryMapping
    public NotificationModel getNotificationById(@Argument Long id, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return notificationService.getNotificationById(id);
    }
}