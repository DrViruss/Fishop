package com.example.fishop.controller;

import com.example.fishop.dto.OrderDTO;
import com.example.fishop.dto.OrderItemDTO;
import com.example.fishop.dto.UserDTO;
import com.example.fishop.dto.response.ResponseItemDTO;
import com.example.fishop.dto.response.ResponseOrderDTO;
import com.example.fishop.dto.response.ResponseUserDTO;
import com.example.fishop.entity.Order;
import com.example.fishop.entity.Product;
import com.example.fishop.entity.User;
import com.example.fishop.entity.embended.OrderedProduct;
import com.example.fishop.enums.UserRoleEnum;
import com.example.fishop.service.OrderService;
import com.example.fishop.service.ProductService;
import com.example.fishop.service.UserService;
import com.example.fishop.util.DBUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.fishop.config.WebSecurityConfig.encoder;

@Controller
public class UserViewController {

    Logger logger = LoggerFactory.getLogger(UserViewController.class);

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;


    @PostMapping(value = "/api/user/update", consumes = {"application/x-www-form-urlencoded"})
    public String update(@Valid UserDTO userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
        {
            if(bindingResult.hasFieldErrors("email")) return "redirect:/user?emailNotValid";
            if(bindingResult.hasFieldErrors("username")) return "redirect:/user?usernameNotValid";
            if (bindingResult.hasFieldErrors("zip")) return "redirect:/user?zipNotValid";
        }

        User user = userService.getByEmail(userDTO.getEmail());
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user == null || ( !Objects.equals(sender.getEmail(), userDTO.getEmail()) && sender.getRole() != UserRoleEnum.ADMIN )) return "redirect:/";


        if(!DBUtils.validateLocation(userDTO.getLocation())) return "redirect:/user?locationNotValid";

        String pass = userDTO.getPassword();
        if(!pass.isBlank())
        {
            if(DBUtils.validatePassword(pass))
                user.setPassword(userDTO.getPassword());
            else
                return "redirect:/user?passwordNotValid";
        }

        logger.debug(sender.getEmail()+" with role: "+ sender.getRole()+" trying to update "+user.getEmail()+"'s information!");

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setZip(userDTO.getZip());
        user.setCountry(userDTO.getCountry());
        user.setState(userDTO.getState());
        user = userService.save(user);

        return "redirect:/user/"+user.getEmail();
    }

    @PostMapping(value = "/api/user/add", consumes = {"application/x-www-form-urlencoded"})
    public String add(@Valid UserDTO userDTO,BindingResult bindingResult) {
        User user = userService.getByEmail(userDTO.getEmail());
        if(user != null) return "redirect:/register?emailexist";


        if(bindingResult.hasErrors())
        {
            if(bindingResult.hasFieldErrors("email")) return "redirect:/register?emailNotValid";
            if(bindingResult.hasFieldErrors("username")) return "redirect:/register?usernameNotValid";
            if (bindingResult.hasFieldErrors("zip")) return "redirect:/register?zipNotValid";
        }

        if(!DBUtils.validateCountry(userDTO.getCountry()) || !DBUtils.validateState(userDTO.getState()))
            return "redirect:/register?locationNotValid";

        user = new User(userDTO.getUsername(),encoder.encode(userDTO.getPassword()),userDTO.getEmail());

        String pass = userDTO.getPassword();
        if(pass.isBlank() || !DBUtils.validatePassword(pass))
        {
                return "redirect:/register?passwordNotValid";
        }

        logger.debug("Anonymous user tries to register with email: "+userDTO.getEmail());

        user.setZip(userDTO.getZip());
        user.setRole(UserRoleEnum.USER);
        user.setCountry(userDTO.getCountry());
        user.setState(userDTO.getState());

        userService.save(user);
        return "redirect:/login";
    }


    @GetMapping("/user/{username}")
    public String userById(
            @PathVariable @NotBlank @Valid @Email String username,
            Model model)
    {
        User usr = userService.getByEmail(username);
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!Objects.equals(sender.getEmail(), usr.getEmail()) && sender.getRole() != UserRoleEnum.ADMIN) return "redirect:/";

        ResponseUserDTO response = new ResponseUserDTO(usr);
        model.addAttribute("user",response);
        return "user/profile";
    }

    @GetMapping("/user")
    public String user(Model model) {
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        ResponseUserDTO response = new ResponseUserDTO(sender);
        model.addAttribute("user",response);

        return "user/profile";
    }

    @GetMapping(value = "/user/cart")
    public String cart(Model model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName() /*sender.getEmail()*/);

        return "user/cart";
    }

    @PostMapping(value = "/api/user/getCartItems")
    public ResponseEntity<List<ResponseItemDTO>> cart(@RequestBody List<Long> items) {
        if(items == null) return ResponseEntity.badRequest().build();
        List<ResponseItemDTO> result = new ArrayList<>();
        for(Long id : items)
        {
            Product product = productService.get(id);
            if(product != null)
                result.add(new ResponseItemDTO(product));
        }
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @PostMapping(value = "/api/user/createOrder")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!Objects.equals(sender.getEmail(), orderDTO.getUsername())) return ResponseEntity.status(403).build();
        List<OrderedProduct> orderItems = new ArrayList<>();
        Order order = null;

        for(OrderItemDTO dto : orderDTO.getItems())
        {
            Product product = productService.get(dto.getId());
            if(product != null)
            {
                orderItems.add(new OrderedProduct(product, dto.getQuantity()));
            }
        }
        if (!orderItems.isEmpty())
        {
            User user = userService.getByEmail(orderDTO.getUsername());
            order = new Order(user,orderItems);
            order = orderService.save(order);
            user.addOrder(order);
            userService.save(user);

            logger.debug("New order #"+order.getId()+" was created by: "+user.getEmail());
        }

        if(order!= null) return ResponseEntity.ok(order.getId());

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/user/order")
    public String orders(@RequestParam(name = "id") Long id,Model model) {
        Order order = orderService.get(id);
        if(order == null) return "redirect:/";
        User user = order.getCustomer();
        if(user == null) return "redirect:/logout";
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!Objects.equals(sender.getEmail(), user.getEmail()) && sender.getRole() != UserRoleEnum.ADMIN) return "redirect:/";


        model.addAttribute("username",user.getEmail());
        model.addAttribute("order",new ResponseOrderDTO(order));

        return "user/order";
    }

}
