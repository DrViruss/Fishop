package com.example.fishop.entity.embended;

import com.example.fishop.entity.Product;
import jakarta.persistence.*;

@Embeddable
public class CartItem {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    private double price;

    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        UpdatePrice();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public void UpdatePrice()
    {
        this.price = product.getPrice() * quantity;
    }
}
