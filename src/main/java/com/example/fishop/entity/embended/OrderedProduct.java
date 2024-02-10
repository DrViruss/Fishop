package com.example.fishop.entity.embended;

import com.example.fishop.entity.Product;
import jakarta.persistence.*;

@Embeddable
public class OrderedProduct {
    private Long id;
    private String name;
    private String specieName;
    private double price;

    private int quantity;

    public OrderedProduct() {
    }

    public OrderedProduct(Product product, int quantity) {
        this.id = product.getId();
        this.name = product.getName();
        this.specieName = product.getSpecie().getName();
        this.price = product.getPrice();
        this.quantity = Math.min(product.getQuantity(), quantity);
    }

    public OrderedProduct(Long id, String name, String specieName, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.specieName = specieName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecieName() {
        return specieName;
    }

    public void setSpecieName(String specieName) {
        this.specieName = specieName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
