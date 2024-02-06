package com.example.fishop.dto;
public class CartItemDTO {
    Long productid;
    Long userid;
    int quantity;

    public CartItemDTO(Long productid, Long userid, int quantity) {
        this.productid = productid;
        this.userid = userid;
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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
