package com.example.fishop.services;

import com.example.fishop.entity.embended.CartItem;
import com.example.fishop.entity.Order;
import com.example.fishop.entity.Product;
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

    public int getItemQuantity(List<CartItem> items)
    {
        int totalCartAmount = 0;
        float singleCartAmount = 0f;
        int availableQuantity = 0;

        for (CartItem item : items) {
//            Optional<Product> product = productRepo.findById(item.getProductId());
//            if(product.isPresent())
//            {
                Product product1 = item.getProduct();
                if(product1.getQuantity() < item.getQuantity())
                {
                    item.setPrice(product1.getQuantity() * product1.getPrice());
                    item.setQuantity(product1.getQuantity());
                }
                else
                {
                    singleCartAmount = (float) (item.getQuantity() * product1.getPrice());
                    availableQuantity = product1.getQuantity() - item.getQuantity();
                }

                totalCartAmount = (int) (totalCartAmount + singleCartAmount);
                product1.setQuantity(availableQuantity);
                availableQuantity=0;
                item.setQuantity((int) singleCartAmount);
                productRepo.save(product1);
//            }

        }
        return totalCartAmount;
    }

    public List<Order> get()
    {
        List<Order> result = new ArrayList<>();
        Iterable<Order> optional = orderRepo.findAll();
        optional.forEach(result::add);
        return result;
    }

    public Order save(Order order)
    {
        return orderRepo.save(order);
    }


}
