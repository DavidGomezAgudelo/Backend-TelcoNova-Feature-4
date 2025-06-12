package com.Fabrica.TelcoNova.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.Fabrica.TelcoNova.model.UserModel;

import graphql.GraphQLContext;

@Component
public class AuthorizationService {

    public UserModel getAuthenticatedUser(GraphQLContext context) {
        UserModel user = context.get("user");


        if (user == null) {
            throw new AccessDeniedException("Acceso denegado. Se requiere autenticación.");
        }
        return user;
    }

    public UserModel validateAdmin(GraphQLContext context) {
        UserModel user = getAuthenticatedUser(context);
        if (user.getRole() == null || !"admin".equalsIgnoreCase(user.getRole().getName())) {
            throw new AccessDeniedException("Acceso denegado. Se requiere rol de administrador.");
        }
        return user;
    }

    public void validateIsOwnerOrAdmin(GraphQLContext context, Long resourceOwnerId) {
        UserModel currentUser = getAuthenticatedUser(context);
        if ("admin".equalsIgnoreCase(currentUser.getRole().getName())) {
            return; 
        }
        if (!currentUser.getId().equals(resourceOwnerId)) {
            throw new AccessDeniedException("Acceso denegado. No tienes permiso para ver la información de otros usuarios.");
        }
    }
}
