package com.example.fishop.dto;

import jakarta.validation.constraints.Email;

import java.util.List;

public class OrderDTO {
    @Email
    String username;
    List<OrderItemDTO> items;

    public OrderDTO(String username, List<OrderItemDTO> items) {
        this.username = username;
        this.items = items;
    }

    public OrderDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
