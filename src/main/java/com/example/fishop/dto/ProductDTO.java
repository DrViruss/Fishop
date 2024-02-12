package com.example.fishop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String specie;
    @NotNull
    @Min(1)
    private double defaultprice;
    @NotNull
    @Min(0)
    private int quantity;
    @NotNull
    @Min(0)
    private int discount;

    public ProductDTO() {
        this.id = -1L;
    }

    public ProductDTO(Long id, String name, String description, String specie, double defaultprice, int quantity, int discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.specie = specie;
        this.defaultprice = defaultprice;
        this.quantity = quantity;
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public double getDefaultprice() {
        return defaultprice;
    }

    public void setDefaultprice(double defaultprice) {
        this.defaultprice = defaultprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
