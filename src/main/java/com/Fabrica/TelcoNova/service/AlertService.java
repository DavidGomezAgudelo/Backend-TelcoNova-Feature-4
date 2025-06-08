package com.Fabrica.TelcoNova.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Fabrica.TelcoNova.dto.CreateAlertInput;
import com.Fabrica.TelcoNova.dto.UpdateAlertInput;
import com.Fabrica.TelcoNova.model.AlertModel;
import com.Fabrica.TelcoNova.repository.AlertRepository;
import com.Fabrica.TelcoNova.repository.EventRepository;

@Service
public class AlertService {
    private final AlertRepository alertRepository;
    private final EventRepository eventRepository;

    public AlertService(AlertRepository alertRepository, EventRepository eventRepository) {
        this.alertRepository = alertRepository;
        this.eventRepository = eventRepository;
    }

    public AlertModel createAlert(CreateAlertInput input) {
        AlertModel alert = new AlertModel();
        alert.setTitle(input.getTitle());
        alert.setDescription(input.getDescription());
        alert.setPriority(input.getPriority());
        alert.setCreatedAt(LocalDateTime.now());
        alert.setActive(true);

        alert.setEvent(eventRepository.findById(input.getEventId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado")));

        alert.setCategory(input.getCategory());

        return alertRepository.save(alert);
    }

    public boolean updateAlert(Long id, UpdateAlertInput input) {
        AlertModel alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));

        if (input.getTitle() != null) alert.setTitle(input.getTitle());
        if (input.getDescription() != null) alert.setDescription(input.getDescription());
        if (input.getPriority() != null) alert.setPriority(input.getPriority());
        if (input.getActive() != null) alert.setActive(input.getActive());
        if (input.getCategory() != null) alert.setCategory(input.getCategory());
        

        alertRepository.save(alert);
        return true;
    }

    public boolean deleteAlert(Long id) {
        AlertModel alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        alertRepository.delete(alert);
        return true;
    }

    public List<AlertModel> getAlerts() {
        return alertRepository.findAll();
    }

    public AlertModel getAlertById(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
    }
}
