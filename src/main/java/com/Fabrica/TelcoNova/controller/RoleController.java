package com.Fabrica.TelcoNova.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.model.RoleModel;
import com.Fabrica.TelcoNova.service.AuthorizationService;
import com.Fabrica.TelcoNova.service.RoleService;

import graphql.GraphQLContext;

@Controller
public class RoleController {

    private final RoleService roleService;
    private final AuthorizationService authorizationService; 

    public RoleController(RoleService roleService, AuthorizationService authorizationService) {
        this.roleService = roleService;
        this.authorizationService = authorizationService;
    }

    @QueryMapping
    public List<RoleModel> getRoles(GraphQLContext context) {
        authorizationService.getAuthenticatedUser(context);
        return roleService.getRoles();
    }

    @MutationMapping
    public RoleModel createRole(@Argument String name, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return roleService.createRole(name);
    }
}
