package com.example.fishop.dto.response;

import com.example.fishop.entity.User;

public class ResponseUserDTO {

    private Long id;
    private String username;
    private int zip;
    private String country;
    private String state;
    private String email;

    public ResponseUserDTO() {
    }

    public ResponseUserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.zip = user.getZip();
        this.country = user.getCountry();
        this.state = user.getState();
        this.email = user.getEmail();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
