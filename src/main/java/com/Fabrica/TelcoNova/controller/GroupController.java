package com.Fabrica.TelcoNova.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.Fabrica.TelcoNova.dto.CreateGroupInput;
import com.Fabrica.TelcoNova.dto.GroupWithUsersDTO;
import com.Fabrica.TelcoNova.model.GroupModel;
import com.Fabrica.TelcoNova.service.AuthorizationService;
import com.Fabrica.TelcoNova.service.GroupService;

import graphql.GraphQLContext;


@Controller
public class GroupController {

    private final GroupService groupService;
    private final AuthorizationService authorizationService; 
    

    public GroupController(GroupService groupService,AuthorizationService authorizationService) {
        this.groupService = groupService;
        this.authorizationService=authorizationService;
    }

    @QueryMapping
    public List<GroupModel> getAllGroups(GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return groupService.getAllGroups();
    }

    @QueryMapping
    public GroupModel getGroupById(@Argument Long id, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return groupService.getGroupById(id);
    }

    @QueryMapping
    public List<GroupWithUsersDTO> getAllGroupsWithUsers( GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return groupService.getAllGroupsWhitUsers();
    }

    @MutationMapping
    public GroupModel createGroup(@Argument CreateGroupInput input, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return groupService.CreateGroup(input);
    }

    @MutationMapping
    public GroupModel addUserToGroup(@Argument Long groupId, @Argument Long userId, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return groupService.addUserToGroup(groupId, userId);
    }


    @MutationMapping
    public GroupModel updateGroup(@Argument Long id, @Argument String name, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return groupService.updateGroup(id, name);
    }

    @MutationMapping
    public GroupModel removeUserFromGroup(@Argument Long groupId, @Argument Long userId, GraphQLContext context) {
        authorizationService.validateAdmin(context);
        return groupService.removeUserFromGroup(groupId, userId);
    }

}
