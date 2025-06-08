package com.Fabrica.TelcoNova.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fabrica.TelcoNova.model.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
     Optional<RoleModel> findByName(String Name);
}
