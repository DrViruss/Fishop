package com.example.fishop.entity;

import com.example.fishop.entity.embended.Cart;
import com.example.fishop.entity.embended.CartItem;
import com.example.fishop.enums.UserRoleEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private int zip;
    private String country;
    private String state;
    @Column(unique=true)
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(fetch = FetchType.LAZY)
    @ElementCollection(targetClass = Order.class)
    private List<Order> orders;

    @Embedded
    private Cart cart;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void defaultCart(){this.cart = new Cart(null,0);}
}

//@Embeddable
//class Cart {
//    @Embedded
//    @ElementCollection(targetClass = CartItem.class)
//    private List<CartItem> items;
//    private float price;
//
//    public Cart(List<CartItem> items, float price) {
//        this.items = items;
//        this.price = price;
//    }
//
//    public Cart() {
//        price = 0;
//    }
//
//    public List<CartItem> getItems() {
//        return items;
//    }
//
//    public void setItems(List<CartItem> items) {
//        this.items = items;
//    }
//
//    public float getPrice() {
//        return price;
//    }
//
//    public void setPrice(float price) {
//        this.price = price;
//    }
//}



