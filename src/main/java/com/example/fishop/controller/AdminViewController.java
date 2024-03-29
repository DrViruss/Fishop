package com.example.fishop.controller;

import com.example.fishop.dto.response.ResponseOrderDTO;
import com.example.fishop.dto.response.ResponseProductDTO;
import com.example.fishop.dto.response.ResponseUserDTO;
import com.example.fishop.service.OrderService;
import com.example.fishop.service.ProductService;
import com.example.fishop.service.ProductSpecieService;
import com.example.fishop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminViewController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductSpecieService specieService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @GetMapping("/adminpanel")
    public String panel()
    {
        return "redirect:/adminpanel/orders";
    }

    @GetMapping("/adminpanel/orders")
    public String orders(Model model)
    {
        List<ResponseOrderDTO> orders = new ArrayList<>();
        orderService.get().forEach(order -> {
            ResponseOrderDTO dto = new ResponseOrderDTO(order);
            orders.add(dto);
        });
        model.addAttribute("isOrders",true);
        model.addAttribute("orders",orders);
        return "admin/adminpanel";
    }


    @GetMapping("/adminpanel/products/new")
    public String addProduct(Model model)
    {
        model.addAttribute("species",specieService.get());
        return "admin/newproduct";
    }

    @GetMapping("/adminpanel/users")
    public String users(Model model)
    {
        List<ResponseUserDTO> users = new ArrayList<>();
        userService.get().forEach(user ->
        {
            ResponseUserDTO dto = new ResponseUserDTO(user);
            users.add(dto);
        });
        model.addAttribute("isUsers",true);
        model.addAttribute("users",users);
        return "admin/adminpanel";
    }
    @GetMapping("/adminpanel/products")
    public String products(Model model)
    {
        List<ResponseProductDTO> products = productService.get();
        model.addAttribute("isProducts",true);
        model.addAttribute("products",products);
        return "admin/adminpanel";
    }
}
