package com.example.fishop.controllers.view;

import com.example.fishop.dto.CartItemDTO;
import com.example.fishop.dto.UserDTO;
//import com.example.fishop.dto.response.*;
import com.example.fishop.dto.response.ResponseItemDTO;
import com.example.fishop.dto.response.ResponseUserDTO;
import com.example.fishop.entity.Product;
import com.example.fishop.entity.User;
import com.example.fishop.entity.embended.Cart;
import com.example.fishop.entity.embended.CartItem;
import com.example.fishop.enums.UserRoleEnum;
import com.example.fishop.services.OrderService;
import com.example.fishop.services.ProductService;
import com.example.fishop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UserViewController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;


    @PostMapping(value = "/api/user/update", consumes = {"application/x-www-form-urlencoded"})
    public String update(UserDTO userDTO) {
        User user = userService.getByEmail(userDTO.getEmail());
        if(user == null) return "redirect:/error";
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!Objects.equals(sender.getEmail(), userDTO.getEmail()) && sender.getRole() != UserRoleEnum.ADMIN) return "redirect:/";

        user.setUsername(userDTO.getUsername());
        if(!userDTO.getPassword().isBlank()) user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setZip(userDTO.getZip());
        user.setCountry(userDTO.getCountry());
        user.setState(userDTO.getState());
//TODO: validation
        user = userService.save(user);
        return "redirect:/user/"+user.getEmail();
    }

    @PostMapping(value = "/api/user/add", consumes = {"application/x-www-form-urlencoded"})
    public String add(UserDTO userDTO) {
        User user = userService.getByEmail(userDTO.getEmail());
        if(user != null)
            return "redirect:/register?error";

        user = new User(userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail());
        user.setZip(userDTO.getZip());
        user.setRole(UserRoleEnum.USER);
        user.setCountry(userDTO.getCountry());
        user.setState(userDTO.getState());
        user.defaultCart();
//TODO: validation
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/user/{username}")
    public String user(@PathVariable String username, Model model) {
        User usr = userService.getByEmail(username);
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!Objects.equals(sender.getEmail(), usr.getEmail()) && sender.getRole() != UserRoleEnum.ADMIN) return "redirect:/";

        ResponseUserDTO response = new ResponseUserDTO(usr);
        model.addAttribute("user",response);
        return "user/profile";
    }

    @PostMapping(value = "/user/cart/add")
    public String addToCart(CartItemDTO itemDTO) { //TODO: ограничить по количеству при заказе!!!!!
        User user = userService.getByEmail(itemDTO.getUsername());
        if(user == null) return "redirect:/logout";
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!Objects.equals(sender.getEmail(), user.getEmail()) && sender.getRole() != UserRoleEnum.ADMIN) return "redirect:/";

        Product product = productService.get(itemDTO.getProductid());
        if(product == null) return "redirect:/all";

        Cart cart = user.getCart();
        cart.addItem(new CartItem(product,itemDTO.getQuantity()));
        cart.UpdatePrice();
        userService.save(user);

        return "redirect:/all/"+itemDTO.getProductid();
    }

    @GetMapping(value = "/user/cart")
    public String cart(@RequestParam(name = "user") String email,Model model) {
        User user = userService.getByEmail(email);
        if(user == null) return "redirect:/logout";
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!Objects.equals(sender.getEmail(), user.getEmail()) && sender.getRole() != UserRoleEnum.ADMIN) return "redirect:/";


//        model.addAttribute("cart",new ResponseCartDTO(user.getCart()));
        model.addAttribute("username",user.getEmail());

//        Product product = productService.get(itemDTO.getProductid());
//        if(product == null) return "redirect:/all";
//
//        Cart cart = user.getCart();
//        cart.addItem(new CartItem(product,itemDTO.getQuantity()));
//        cart.UpdatePrice();
//        userService.save(user);

        return "user/cart";
    }

    @PostMapping(value = "/user/cart/remove") //TODO: make useful
    public String removeFromCart(CartItemDTO itemDTO) {
        User user = userService.getByEmail(itemDTO.getUsername());
        if(user == null) return "redirect:/logout";
        User sender = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!Objects.equals(sender.getEmail(), user.getEmail()) && sender.getRole() != UserRoleEnum.ADMIN) return "redirect:/";

        Product product = productService.get(itemDTO.getProductid());
        if(product == null) return "redirect:/all";

        Cart cart = user.getCart();
        cart.editItem(product,itemDTO.getQuantity());
        cart.UpdatePrice();
        userService.save(user);

        return "redirect:/all/"+itemDTO.getProductid();
    }


    @PostMapping(value = "/api/user/getCartItems")
    public ResponseEntity<List<ResponseItemDTO>> cart(@RequestBody List<Long> items) {
        List<ResponseItemDTO> result = new ArrayList<>();
        for(Long id : items)
        {
            Product product = productService.get(id);
            if(product != null)
                result.add(new ResponseItemDTO(product));
        }
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }


//    @PreAuthorize("#userid == authentication.principal.id")
//    @GetMapping(value = "/user/cart/make")
//    public String makeOrder(@RequestParam(defaultValue = "0") Long userid, Model model) {
//        Order order = orderService.get(userid);
//        model.addAttribute("order",order);
//        //TODO: open payment page ???
//        return "order";
//    }
//
//    @PreAuthorize("#userid == authentication.principal.id")
//    @PostMapping(value = "/api/user/order/create")
//    public String submitOrder(@RequestParam(defaultValue = "0") Long userid,ShippingDataDTO) {
//        User user = userService.getById(userid);
//        Order order = new Order(user,user.getCart());
//
//
//
//        if(user == null) return "redirect:/logout";
//        //TODO: empty order creation  ???
//        return "redirect:/all/";
//    }
//
//    @PostMapping(value = "/api/user/order/done")
//    public String doneOrder(@RequestParam(defaultValue = "0") Long userid, @RequestParam(defaultValue = "0") Long orderid) {
//        Order order = orderService.get(orderid);
//        if(!Objects.equals(order.getCustomer().getId(), userid)) return "redirect:/403";
//        User user = userService.getById(userid);
//
//
////        Order order = new Order(user,user.getCart());
//        if(user == null) return "redirect:/logout";
//        //TODO: payment  ???
//        return "redirect:/all/";
//    }
}
