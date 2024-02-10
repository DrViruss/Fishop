package com.example.fishop.dto;

import jakarta.validation.constraints.*;

public class PaymentDTO {
    @NotNull
    private Long id;
    @NotNull
    @Min(1)
    private double price;
    @Email
    private String email;
    @NotBlank
    @Size(min=5, max = 200)
    private String productName;

    public PaymentDTO() {
    }

    public PaymentDTO(Long id, double price, String email, String productName) {
        this.id = id;
        this.price = price;
        this.email = email;
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
