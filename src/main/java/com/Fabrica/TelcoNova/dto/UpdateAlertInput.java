package com.Fabrica.TelcoNova.dto;

public class UpdateAlertInput {
    private String title;
    private String description;
    private Integer priority;
    private Boolean active;
    private String category;

    public UpdateAlertInput(){}
    public UpdateAlertInput(Boolean active, String category, String description, Integer priority, String title) {
        this.active = active;
        this.category = category;
        this.description = description;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    


}

