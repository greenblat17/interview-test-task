package com.greenblat.rest.dto;

public class PersonDTO {

    private String username;
    private String password;
    private String email;
    private String imageUri;

    public PersonDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public PersonDTO() {
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
