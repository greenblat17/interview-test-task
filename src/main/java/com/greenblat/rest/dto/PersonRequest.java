package com.greenblat.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenblat.rest.models.Status;

public class PersonRequest {

    private String username;
    private String password;
    private String email;
    private Status status;
    private String imageUri;

    public PersonRequest(String username, String password, String email, Status status ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    public PersonRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
