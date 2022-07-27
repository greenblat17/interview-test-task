package com.greenblat.rest.dto;

import com.greenblat.rest.models.Status;

public class StatusResponse {
    private Long id;
    private Status newStatus;
    private Status oldStatus;

    public StatusResponse() {
    }

    public StatusResponse(Status newStatus, Status oldStatus) {
        this.newStatus = newStatus;
        this.oldStatus = oldStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Status newStatus) {
        this.newStatus = newStatus;
    }

    public Status getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Status oldStatus) {
        this.oldStatus = oldStatus;
    }
}
