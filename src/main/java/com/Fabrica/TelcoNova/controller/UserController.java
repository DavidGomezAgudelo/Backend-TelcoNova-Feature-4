package com.Fabrica.TelcoNova.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.dto.CreateUserInput;
import com.Fabrica.TelcoNova.model.UserModel;
import com.Fabrica.TelcoNova.service.AuthorizationService;
import com.Fabrica.TelcoNova.service.UserService;

import graphql.GraphQLContext;

@Controller 
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService; 

    public UserController(UserService userService,AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService=authorizationService;
    }

    @QueryMapping 
    public UserModel getUser(@Argument Long id,GraphQLContext context) {
        authorizationService.validateIsOwnerOrAdmin(context,id);
        return userService.getUserById(id);
    }

    @QueryMapping 
    public List<UserModel> getAllUsers(GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return userService.getAllUsers();
    }

    @MutationMapping
    public UserModel createUser(@Argument CreateUserInput input,GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return userService.createUser(input);
    }

    
}