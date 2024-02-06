package com.example.fishop.dto;
public class CartItemDTO {
    Long productid;
    String username;
    int quantity;

    public CartItemDTO(Long productid, String username, int quantity) {
        this.productid = productid;
        this.username = username;
        this.quantity = quantity;
    }

    public CartItemDTO() {
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
