package com.example.fishop.dto.response;

import com.example.fishop.entity.Product;

public class ResponseItemDTO {
    Long id;
    String productname;
    String speciename;
    double productprice;
    int quantity;


    public ResponseItemDTO(Product product)
    {
        this.id = product.getId();
        this.productname = product.getName();
        this.speciename = product.getSpecie().getName();
        this.productprice = product.getPrice();
        this.quantity = product.getQuantity();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getProductprice() {
        return productprice;
    }

    public void setProductprice(double productprice) {
        this.productprice = productprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSpeciename() {
        return speciename;
    }

    public void setSpeciename(String speciename) {
        this.speciename = speciename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
