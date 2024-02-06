package com.example.fishop.dto;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private int zip;
    private String location;

    public UserDTO(String username, String password, String email, int zip) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.zip = zip;
    }

    public UserDTO() {
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

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        String[] loc = location.split(",");
        return loc[0].isBlank()? "UNKNOWN_COUNTRY" : loc[0].trim();
    }

    public String getState() {
        String[] loc = location.split(",");
        return loc[1].isBlank()? "UNKNOWN_STATE" : loc[1].trim();
    }
}
