package com.example.fishop.entity;

import com.example.fishop.entity.embended.OrderedProduct;
import com.example.fishop.enums.OrderStatusEnum;
import com.example.fishop.util.DBUtils;
import jakarta.persistence.*;

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
    @ElementCollection(targetClass = OrderedProduct.class)
    private List<OrderedProduct> items;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    private String date;
    private float price;
    private float amount;
    private String paymentIntent;
    private String paymentIntentClientSecret;


    public Order(User customer,List<OrderedProduct> products) {
        this.customer = customer;
        this.items = products;
        this.status = OrderStatusEnum.AWAITING_PAYMENT;
        this.date = DBUtils.getCurrentDateTime();
        InitItemsData();
    }

    public Order() {
    }

    public String getPaymentIntent() {
        return paymentIntent;
    }

    public void setPaymentIntent(String paymentIntent) {
        this.paymentIntent = paymentIntent;
    }

    public String getPaymentIntentClientSecret() {
        return paymentIntentClientSecret;
    }

    public void setPaymentIntentClientSecret(String paymentIntentClientSecret) {
        this.paymentIntentClientSecret = paymentIntentClientSecret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderedProduct> getItems() {
        return items;
    }

    public void setItems(List<OrderedProduct> items) {
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
        for(OrderedProduct item : items)
        {
            price += (float) (item.getPrice() * item.getQuantity());
            amount += item.getQuantity();
        }
    }
}
