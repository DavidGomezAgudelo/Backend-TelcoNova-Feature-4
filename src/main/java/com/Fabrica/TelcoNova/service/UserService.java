package com.Fabrica.TelcoNova.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fabrica.TelcoNova.dto.CreateUserInput;
import com.Fabrica.TelcoNova.model.RoleModel;
import com.Fabrica.TelcoNova.model.UserModel;
import com.Fabrica.TelcoNova.repository.RoleRepository;
import com.Fabrica.TelcoNova.repository.UserRepository;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);  
    }

     public UserModel createUser(CreateUserInput input) {
        UserModel user = new UserModel();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPhone(input.getPhone());
        user.setAddress(input.getAddress());
        user.setCreatedAt(LocalDateTime.now());

        RoleModel role = roleRepository.findById(input.getRoleId())
            .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        return userRepository.save(user);
    }
    
    public UserModel getDefaultUser() {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("123-456-7890");
        user.setAddress("123 Main St");
       

        RoleModel role = new RoleModel();
        
        role.setId((long) 1);
        role.setName("Admin");
        user.setRole(role);

        return user;
    }
}
