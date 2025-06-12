package com.Fabrica.TelcoNova.dto;

public class CreateAlertInput {
    private String title;
    private String description;
    private Integer priority;
    private Long eventId;
    private String category;

    public CreateAlertInput(){

    }
    public CreateAlertInput(String category, String description, Long eventId, Integer priority, String title) {
        this.category = category;
        this.description = description;
        this.eventId = eventId;
        this.priority = priority;
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    

}


    
