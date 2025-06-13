package com.Fabrica.TelcoNova.dto;

import java.util.List;

public class SendNotificationInput {

    private Long alertId;
    private List<Long> groupIds;
    private List<Long> deliveryMethodIds;
    private String scheduledDate; 

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public List<Long> getDeliveryMethodIds() {
        return deliveryMethodIds;
    }

    public void setDeliveryMethodIds(List<Long> deliveryMethodIds) {
        this.deliveryMethodIds = deliveryMethodIds;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}