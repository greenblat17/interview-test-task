package com.greenblat.rest.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonRequest {

    @NotEmpty(message = "Name should not be empty")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, max = 30, message = "Password's length should be greater than 8")
    private String password;

    @NotEmpty(message = "Message should not be empty")
    @Email(message = "Email is wrong")
    private String email;

    private String imageUri;

    public PersonRequest(String username, String password, String email, String imageUri) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageUri = imageUri;
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
}
