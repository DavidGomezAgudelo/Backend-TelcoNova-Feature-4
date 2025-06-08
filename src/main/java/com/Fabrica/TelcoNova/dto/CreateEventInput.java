package com.Fabrica.TelcoNova.dto;

public class CreateEventInput {
    private Long eventTypeId;
    private String description;
    private String eventDate;

    
    public Long getEventTypeId() {
        return eventTypeId;
    }


    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getEventDate() {
        return eventDate;
    }


    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

     public CreateEventInput(){
        
     }
    public CreateEventInput(Long eventTypeId, String description, String eventDate) {
        this.eventTypeId = eventTypeId;
        this.description = description;
        this.eventDate = eventDate;
    }

    
}
