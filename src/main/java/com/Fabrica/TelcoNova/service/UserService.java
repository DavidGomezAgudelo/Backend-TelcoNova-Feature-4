package com.Fabrica.TelcoNova.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fabrica.TelcoNova.dto.CreateUserInput;
import com.Fabrica.TelcoNova.model.RoleModel;
import com.Fabrica.TelcoNova.model.UserModel;
import com.Fabrica.TelcoNova.repository.RoleRepository;
import com.Fabrica.TelcoNova.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);  
    }

    public  List<UserModel> getAllUsers(){
        return userRepository.findAll();
    }

     public UserModel findOrCreateUser(String email, String name) {
    Optional<UserModel> optionalUser = userRepository.findByEmail(email);
    
    if (optionalUser.isPresent()) {
        return optionalUser.get();
    }

    UserModel user = new UserModel();
    user.setEmail(email);
    user.setName(name);

    // Asignar rol por defecto (ej: "USER")
    RoleModel defaultRole = roleRepository.findByName("user")
        .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));

    user.setRole(defaultRole);

    return userRepository.save(user);
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
