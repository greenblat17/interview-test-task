package com.greenblat.rest.dto;

import com.greenblat.rest.models.Status;

public class PersonResponse {
    private String username;
    private String email;
    private Status status;
    private String imageUri;

    public PersonResponse(String username, String password, String email, Status status ) {
        this.username = username;
        this.email = email;
        this.status = status;
    }

    public PersonResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
