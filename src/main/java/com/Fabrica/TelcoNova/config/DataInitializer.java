package com.Fabrica.TelcoNova.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Fabrica.TelcoNova.model.DeliveryMethodModel;
import com.Fabrica.TelcoNova.model.DeliveryStatusModel;
import com.Fabrica.TelcoNova.model.EventModel;
import com.Fabrica.TelcoNova.model.EventTypeModel;
import com.Fabrica.TelcoNova.model.RoleModel;
import com.Fabrica.TelcoNova.model.UserModel;
import com.Fabrica.TelcoNova.repository.DeliveryMethodRepository;
import com.Fabrica.TelcoNova.repository.DeliveryStatusRepository;
import com.Fabrica.TelcoNova.repository.EventRepository;
import com.Fabrica.TelcoNova.repository.EventTypeRepository;
import com.Fabrica.TelcoNova.repository.RoleRepository;
import com.Fabrica.TelcoNova.repository.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final EventTypeRepository eventTypeRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final DataSource dataSource;  // Inyectamos DataSource

    @Autowired
    public DataInitializer(RoleRepository roleRepository,
                          EventTypeRepository eventTypeRepository,
                          DeliveryMethodRepository deliveryMethodRepository,
                          DeliveryStatusRepository deliveryStatusRepository,
                          UserRepository userRepository,
                          EventRepository eventRepository,
                          DataSource dataSource) {  // Añadimos DataSource
        this.roleRepository = roleRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.deliveryMethodRepository = deliveryMethodRepository;
        this.deliveryStatusRepository = deliveryStatusRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.dataSource = dataSource;
    }

    @Transactional
    public void run(String... args) {
        try {
            Boolean F1 =false;
            // 1. Verificar conexión y existencia de tablas
            if (F1){
                
            
            if ((testConnection() && tableExists("roles") )) {
                initLookupTables();
                initTestData();
                System.out.println("✅ Datos inicializados correctamente");
            } else {
                System.err.println("⛔ No se pudo inicializar datos. Verifique la conexión a BD y tablas");
            }
            } else {
                System.out.println("No se inicializarán datos");
            }
        } catch (Exception e) {
            System.err.println("🔥 Error crítico en inicialización: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean testConnection() {
        System.out.println("🔌 Probando conexión a BD...");
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("✅ Conexión exitosa a: " + meta.getDatabaseProductName());
            System.out.println("🔗 URL: " + meta.getURL());
            System.out.println("👤 Usuario: " + meta.getUserName());
            return true;
        } catch (SQLException e) {
            System.err.println("🚨 Error de conexión: " + e.getMessage());
            return false;
        }
    }

    private boolean tableExists(String tableName) {
        System.out.println("🔍 Verificando existencia de tabla: " + tableName);
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("🚨 Error verificando tabla: " + e.getMessage());
            return false;
        }
    }

    private void initLookupTables() {
        System.out.println("⚙️ Inicializando tablas de referencia...");
        
        // Roles
        if (roleRepository.count() == 0) {
            roleRepository.save(new RoleModel("admin"));
            roleRepository.save(new RoleModel("user"));
            System.out.println("👥 Roles creados");
            roleRepository.findAll();
        }
        else{
            System.out.println("👥 Roles NO creados");
            roleRepository.save(new RoleModel("test"));
    roleRepository.save(new RoleModel("user"));
                        roleRepository.findAll();

        }
        if (roleRepository.count() == 0) {
    roleRepository.save(new RoleModel("test"));
    roleRepository.save(new RoleModel("user"));
    System.out.println("👥 Roles creados:");
} else {
    System.out.println("👥 Roles ya existentes:");
}

    // Imprimir todos los roles
    roleRepository.findAll().forEach(role -> System.out.println("🔸 " + role.getName()));

        // Tipos de Evento
        if (eventTypeRepository.count() == 0) {
            eventTypeRepository.save(new EventTypeModel("INFO", 3));
            eventTypeRepository.save(new EventTypeModel("WARNING", 2));
            eventTypeRepository.save(new EventTypeModel("ERROR", 1));
            System.out.println("📝 Tipos de evento creados");
        }

        // Métodos de Entrega
        if (deliveryMethodRepository.count() == 0) {
            deliveryMethodRepository.save(new DeliveryMethodModel("EMAIL"));
            deliveryMethodRepository.save(new DeliveryMethodModel("SMS"));
            deliveryMethodRepository.save(new DeliveryMethodModel("PUSH"));
            System.out.println("📦 Métodos de entrega creados");
        }

        // Estados de Entrega
        if (deliveryStatusRepository.count() == 0) {
            deliveryStatusRepository.save(new DeliveryStatusModel("PENDING"));
            deliveryStatusRepository.save(new DeliveryStatusModel("SENT"));
            deliveryStatusRepository.save(new DeliveryStatusModel("FAILED"));
            System.out.println("📭 Estados de entrega creados");
        }
    }

    private void initTestData() {
        System.out.println("🧪 Creando datos de prueba...");
        
        // Usuario Admin
        if (userRepository.findByEmail("admin@telconova.com").isEmpty()) {
            RoleModel adminRole = roleRepository.findByName("admin")
                    .orElseThrow(() -> new IllegalStateException("Rol admin no encontrado"));
            
            UserModel admin = new UserModel();
            admin.setName("Admin User");
            admin.setEmail("admin@telconova.com");
            admin.setPhone("+1234567890");
            admin.setRole(adminRole);
            admin.setAddress("TelcoNova HQ");
            userRepository.save(admin);
            System.out.println("👑 Usuario admin creado");
        }

        // Evento Inicial
        if (eventRepository.count() == 0) {
            EventTypeModel infoType = eventTypeRepository.findByName("INFO")
                    .orElseThrow(() -> new IllegalStateException("Tipo de evento INFO no encontrado"));
            
            EventModel event = new EventModel();
            event.setEventType(infoType);
            event.setDescription("Sistema inicializado correctamente");
            event.setEventDate(LocalDateTime.now());
            eventRepository.save(event);
            System.out.println("🪧 Evento inicial creado");
        }
    }
}