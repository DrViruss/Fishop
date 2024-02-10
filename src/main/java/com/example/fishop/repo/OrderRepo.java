package com.example.fishop.repo;

import com.example.fishop.entity.Order;
import com.example.fishop.entity.ProductSpecie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {

    public Order findByPaymentIntent(String payment_intent);
}
