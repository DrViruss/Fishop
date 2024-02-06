package com.example.fishop.controllers.api;

import com.example.fishop.dto.OrderDTO;
import com.example.fishop.dto.response.ResponseOrderDTO;
import com.example.fishop.entity.Order;
import com.example.fishop.entity.User;
import com.example.fishop.services.OrderService;
import com.example.fishop.services.UserService;
import com.example.fishop.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@RestController
//@RequestMapping("/api/order")
public class OrderController {
//    UserService userService;
//    OrderService orderService;
//
//    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
//
//    public OrderController(UserService userService, OrderService orderService) {
//        this.userService = userService;
//        this.orderService = orderService;
    }

//    @GetMapping(value = "/getOrder/byUser/{userId}")
//    public ResponseEntity<List<ResponseOrderDTO>> getOrdersByUser(@PathVariable Long userId) {
//        User user = userService.getById(userId);
//        List<ResponseOrderDTO> result = new ArrayList<>();
//        user.getOrders().forEach(order -> {
//            ResponseOrderDTO dto = new ResponseOrderDTO(order);
//            result.add(dto);
//        });
//        return ResponseEntity.ofNullable(result);
//    }
//
//    @PostMapping(value="/placeOrder", consumes = {"application/x-www-form-urlencoded"})
//    public ResponseEntity<ResponseOrderDTO> placeOrder(OrderDTO orderDTO) {
//        logger.info("Request Payload " + orderDTO.toString());
//        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
//        int amount = orderService.getItemQuantity(orderDTO.getCartItems());
//
//        User customer = new User(orderDTO.getCustomerName(), orderDTO.getCustomerEmail());
//        Long customerIdFromDb = userService.isPresent(customer);
//        if (customerIdFromDb != null) {
//            customer.setId(customerIdFromDb);
//            logger.info("Customer already present in db with id : " + customerIdFromDb);
//        }else{
//            customer = userService.save(customer);
//            logger.info("Customer saved.. with id : " + customer.getId());
//        }
//        Order order = new Order(customer, orderDTO.getCartItems());
//        order.setDate(DateUtils.getCurrentDateTime());
//        order.setAmount(amount);
//        order = orderService.save(order);
//        logger.info("Order processed successfully..");
//
//        responseOrderDTO = new ResponseOrderDTO(order);
//        logger.info("test push..");
//
//        return ResponseEntity.ok(responseOrderDTO);
//    }
//
//    @GetMapping(value = "/getOrders")
//    public ResponseEntity<List<ResponseOrderDTO>> getOrders() {
//        List<ResponseOrderDTO> result = new ArrayList<>();
//        orderService.get().forEach(order -> {
//            ResponseOrderDTO dto = new ResponseOrderDTO(order);
//            result.add(dto);
//        });
//        return ResponseEntity.ok(result);
//    }

//}
