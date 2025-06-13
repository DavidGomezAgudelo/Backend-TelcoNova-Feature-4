package com.Fabrica.TelcoNova.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fabrica.TelcoNova.dto.CreateGroupInput;
import com.Fabrica.TelcoNova.dto.GroupWithUsersDTO;
import com.Fabrica.TelcoNova.model.GroupModel;
import com.Fabrica.TelcoNova.model.UserGroupModel;
import com.Fabrica.TelcoNova.model.UserModel;
import com.Fabrica.TelcoNova.repository.GroupRepository;
import com.Fabrica.TelcoNova.repository.UserGroupRepository;
import com.Fabrica.TelcoNova.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserGroupRepository userGroupRepository;

    public GroupModel CreateGroup(CreateGroupInput groupInput) {
    GroupModel grupo = new GroupModel();
    grupo.setName(groupInput.getName());
    GroupModel group=groupRepository.save(grupo);
    
    for (Long userId : groupInput.getUsers()) {
            Optional<UserModel> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                UserGroupModel userGroup = new UserGroupModel();
                userGroup.setGroup(group);
                userGroup.setUser(userOpt.get());
                userGroupRepository.save(userGroup);
            } 
        }
        return grupo;

    }

    public List<GroupModel> getAllGroups() {
        return groupRepository.findAll();
    }

    public GroupModel getGroupById(Long id) {
        return groupRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado con ID: " + id));
    }

    public List<GroupWithUsersDTO> getAllGroupsWhitUsers() {
    List<GroupModel> groups = groupRepository.findAll();
    List<GroupWithUsersDTO> result = new ArrayList<>();

    for (GroupModel group : groups) {
        List<UserModel> users = new ArrayList<>();

        for (UserGroupModel ug : group.getUsers()) {
            UserModel user = ug.getUser();
            users.add(user);
        }

        result.add(new GroupWithUsersDTO(group.getId(), group.getName(), users));
    }

    return result;
    }

    public GroupModel addUserToGroup(Long groupId, Long userId) {
        Optional<GroupModel> groupOpt = groupRepository.findById(groupId);
        Optional<UserModel> userOpt = userRepository.findById(userId);

        if (groupOpt.isEmpty() || userOpt.isEmpty()) {
          throw new RuntimeException("Group or User not found");
            }
        GroupModel group = groupOpt.get();
        UserModel user = userOpt.get();
        UserGroupModel userGroup = new UserGroupModel();
        userGroup.setGroup(group);
        userGroup.setUser(user);
        userGroupRepository.save(userGroup);
        
        return group;
    }

    

    public GroupModel updateGroup(Long groupId, String newName) {
        GroupModel group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado con ID: " + groupId));
        group.setName(newName);
        return groupRepository.save(group);
    }

   
    public GroupModel removeUserFromGroup(Long groupId, Long userId) {
        userGroupRepository.deleteByGroupIdAndUserId( groupId ,  userId);

        GroupModel group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado con ID: " + groupId));
        
        return group;
    }
}
