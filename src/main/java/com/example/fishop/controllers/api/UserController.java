package com.example.fishop.controllers.api;

import com.example.fishop.dto.UserDTO;
import com.example.fishop.entity.User;
import com.example.fishop.enums.UserRoleEnum;
import com.example.fishop.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/remove")
    public ResponseEntity<?>  remove(@RequestBody Long id) {
        userService.remove(id);
        return ResponseEntity.ok().build();
    }
}
