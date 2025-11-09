package com.arqui.travelservice.dto.request;

public class UserRequestDTO {
    private String name;
    private String email;
    private Long accountId;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
