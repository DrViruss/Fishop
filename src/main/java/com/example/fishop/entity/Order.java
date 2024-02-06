package com.example.fishop.entity;

import com.example.fishop.entity.embended.Cart;
import com.example.fishop.entity.embended.CartItem;
import com.example.fishop.enums.OrderStatusEnum;
import com.example.fishop.utils.DateUtils;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private User customer;

    @Embedded
    @ElementCollection(targetClass = CartItem.class)
    private List<CartItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    private String date;
    private float price;
    private float amount;


    public Order(User customer, Cart cart) {
        this.customer = customer;
        this.items = cart.getItems();
        this.status = OrderStatusEnum.AWAITING_PAYMENT;
        this.date = DateUtils.getCurrentDateTime();
        InitItemsData();
    }

    public Order() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    private void InitItemsData()
    {
        this.amount = 0;
        this.price = 0;
        for(CartItem item : items)
        {
            price += item.getPrice();
            amount += item.getQuantity();
        }
    }

}
