package com.example.fishop.entity.embended;

import com.example.fishop.entity.Product;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Deprecated
@Embeddable
public class Cart {
    @Embedded
    @ElementCollection(targetClass = CartItem.class)
    private List<CartItem> items;
    private float price;

    public Cart(List<CartItem> items, float price) {
        this.items = items;
        this.price = price;
    }

    public Cart() {
        price = 0;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void addItem(CartItem item)
    {
        if(items == null)  items = new ArrayList<>();
        if (item == null) return;
        items.add(item);
    }

    public void editItem(Product product,int quantity)
    {
        if(items == null) return;
        CartItem cartItem = getItemById(product.getId());
        if(cartItem != null)
        {
            cartItem.setQuantity(cartItem.getQuantity()-quantity);
            if(cartItem.getQuantity()<=0) items.remove(cartItem);
        }
    }

    private CartItem getItemById(Long id)
    {
        for (CartItem item : items)
            if(Objects.equals(item.getProduct().getId(), id)) return item;
        return null;
    }

    public void UpdatePrice()
    {
        this.price = 0;
        this.items.forEach(i -> {
            this.price += i.getPrice();
        });
    }
}
