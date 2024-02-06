package com.example.fishop.entity;

import jakarta.persistence.*;

import java.text.DecimalFormat;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "specie_id")
    private ProductSpecie specie;
    private double price;
    private double defaultPrice;
    private int quantity;
    private boolean isInStock;
    private int discount;
    private float rating;

    public Product() {
        this.price = 0;
        this.defaultPrice = 0;
        this.discount = 0;
        this.isInStock = false;
        this.quantity = 0;
        this.rating = 0;
    }

    public Product(String name, String description, double defaultPrice, int quantity, ProductSpecie specie) {
        this.name = name;
        this.description = description;
        this.defaultPrice = defaultPrice;
        this.quantity = quantity;
        this.specie = specie;
        UpdateData();
    }

    public Product(String name, String description, ProductSpecie specie, double defaultPrice, int quantity, int discount) {
        this.name = name;
        this.description = description;
        this.specie = specie;
        this.defaultPrice = defaultPrice;
        this.quantity = quantity;
        this.discount = discount;
        UpdateData();
    }

    private void UpdatePrice()
    {
        double num = Math.ceil(defaultPrice - (defaultPrice * ((double) discount /100)));
        this.price = Math.ceil(num * 100) / 100;
    }
    private void UpdateStock()
    {
        this.isInStock = this.quantity > 0;
    }

    public void UpdateData()
    {
        UpdatePrice();
        UpdateStock();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProductSpecie getSpecie() {
        return specie;
    }

    public void setSpecie(ProductSpecie specie) {
        this.specie = specie;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getRating() {
        return (int) rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
