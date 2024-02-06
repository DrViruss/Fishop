package com.example.fishop.controllers.view;

import com.example.fishop.dto.CartItemDTO;
import com.example.fishop.dto.UserDTO;
import com.example.fishop.dto.response.ResponseProductDTO;
import com.example.fishop.dto.response.ResponseUserDTO;
import com.example.fishop.entity.Product;
import com.example.fishop.entity.User;
import com.example.fishop.entity.embended.Cart;
import com.example.fishop.entity.embended.CartItem;
import com.example.fishop.enums.UserRoleEnum;
import com.example.fishop.services.ProductService;
import com.example.fishop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainViewController {
//    @GetMapping("/")
//    public String home(Model model)
//    {
//        model.addAttribute("title","Home");
//        return "home";
//    }
//    @GetMapping("/about")
//    public String about()
//    {
//        return "about";
//    }
//
//    @GetMapping("/register")
//    public String reg()
//    {
//        return "register";
//    }

}
