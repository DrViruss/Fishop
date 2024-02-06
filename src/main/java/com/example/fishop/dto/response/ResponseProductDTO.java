package com.example.fishop.dto.response;

import com.example.fishop.entity.Product;

public class ResponseProductDTO {
    private Long id;
    private String name;
    private String description;
    private String specie;
    private double price;
    private double defaultprice;
    private int quantity;
    private boolean isInStock;
    private int discount;
    private float rating;

    public ResponseProductDTO() {
    }

    public ResponseProductDTO(Product product)
    {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.specie = product.getSpecie().getName();
        this.price = product.getPrice();
        this.defaultprice = product.getDefaultPrice();
        this.quantity = product.getQuantity();
        this.isInStock = product.isInStock();
        this.discount = product.getDiscount();
        this.rating = product.getRating();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
