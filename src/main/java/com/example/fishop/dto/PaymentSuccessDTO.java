package com.example.fishop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentSuccessDTO {
    @NotNull
    Long order_id;
    @Email
    String email;
    @NotBlank
    String status;

    public PaymentSuccessDTO(Long order_id, String email, String status) {
        this.order_id = order_id;
        this.email = email;
        this.status = status;
    }

    public PaymentSuccessDTO() {
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
