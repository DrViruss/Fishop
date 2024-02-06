package com.example.fishop.dto;

public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String specie;
    private double defaultprice;
    private int quantity;
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
