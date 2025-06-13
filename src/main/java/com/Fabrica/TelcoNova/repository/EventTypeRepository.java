package com.Fabrica.TelcoNova.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fabrica.TelcoNova.model.EventTypeModel;

@Repository
public interface EventTypeRepository extends JpaRepository<EventTypeModel, Long> {
    Optional<EventTypeModel> findByName(String Name);
}