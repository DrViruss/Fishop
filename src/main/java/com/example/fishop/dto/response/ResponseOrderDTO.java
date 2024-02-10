package com.example.fishop.dto.response;

import com.example.fishop.entity.Order;
import com.example.fishop.entity.User;
import com.example.fishop.entity.embended.OrderedProduct;
import com.example.fishop.enums.OrderStatusEnum;

import java.util.List;

public class ResponseOrderDTO {
    private Long id;
    private ResponseUserDTO customer;
    private List<OrderedProduct> items;
    private OrderStatusEnum status;
    private float price;

    public ResponseOrderDTO() {
    }

    public ResponseOrderDTO(Order order) {
        this.id = order.getId();
        this.customer = new ResponseUserDTO(order.getCustomer());
        this.items = order.getItems();
        this.status = order.getStatus();
        this.price = order.getPrice();
    }

    public ResponseOrderDTO(User customer, List<OrderedProduct> items, OrderStatusEnum status) {
        this.customer = new ResponseUserDTO(customer);
        this.items = items;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResponseUserDTO getCustomer() {
        return customer;
    }

    public void setCustomer(ResponseUserDTO customer) {
        this.customer = customer;
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
}
