package com.example.fishop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6,max = 12)
    private String username;
    private String password;
    @NotBlank
    @Size(min=5,max = 10)
    @Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$")
    private String zip;
    private String country;
    private String location;
    private String state;

    public UserDTO(String email, String username, String password, String zip, String country, String location, String state) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.zip = zip;
        this.country = country;
        this.location = location;
        this.state = state;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLocation()
    {
        if(location == null)
        {
            String c = country.substring(0, 1).toUpperCase() + country.substring(1);
            String s = state.substring(0, 1).toUpperCase() + state.substring(1);
            return c+", "+s;
        }
        return location;
    }

    public String getCountry() {
        if(country == null)
        {
            String[] loc = this.location.split(",");
            loc[0] = loc[0].trim();
            return loc[0].substring(0, 1).toUpperCase() + loc[0].substring(1);
        }
        return country;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        if(state == null)
        {
            String[] loc = this.location.split(",");
            loc[1] = loc[1].trim();
            return loc[1].substring(0, 1).toUpperCase() + loc[1].substring(1);
        }
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
