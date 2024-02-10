package com.example.fishop.services;

import com.example.fishop.entity.Order;
import com.example.fishop.entity.Product;
import com.example.fishop.entity.embended.OrderedProduct;
import com.example.fishop.repo.OrderRepo;
import com.example.fishop.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    OrderRepo orderRepo;
    ProductRepo productRepo;

    public OrderService(OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public Long isPresent(Order order){
        Order product1 = get(order.getId());
        if(product1 == null) return null;
        return product1.getId();
    }

    public Order get(Long id)
    {
        Optional<Order> optional = orderRepo.findById(id);
        return optional.orElse(null);
    }

    public List<Order> get()
    {
        List<Order> result = new ArrayList<>();
        Iterable<Order> optional = orderRepo.findAll();
        optional.forEach(result::add);
        return result;
    }

    public Order getByIntent(String intent)
    {
        return orderRepo.findByPaymentIntent(intent);
    }

    public Order save(Order order)
    {
        return orderRepo.save(order);
    }


}
