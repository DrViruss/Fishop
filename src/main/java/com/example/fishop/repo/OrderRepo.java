package com.example.fishop.repo;

import com.example.fishop.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order,Long> {
}
