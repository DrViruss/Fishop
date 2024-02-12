package com.example.fishop.controller;

import com.example.fishop.dto.PaymentDTO;
import com.example.fishop.entity.Order;
import com.example.fishop.entity.Product;
import com.example.fishop.entity.embended.OrderedProduct;
import com.example.fishop.enums.OrderStatusEnum;
import com.example.fishop.service.EmailService;
import com.example.fishop.service.OrderService;
import com.example.fishop.service.ProductService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class PaymentController {

    Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    EmailService emailService;

    @Value("${stripe.api.publicKey}")
    private String publicKey;
    @GetMapping("/payment_TEST")
    public String home(@RequestParam Long id,@RequestParam String email, Model model){
        Order o = orderService.get(id);
        if(o == null) return "redirect:/user/cart";
        model.addAttribute("request", new PaymentDTO(id,o.getPrice(),email,"Order #"+id));
        return "payment/index";
    }

    @PostMapping("/payment_TEST")
    public String checkout(@ModelAttribute @Valid PaymentDTO paymentDTO,
                           BindingResult bindingResult,
                           Model model){
        if (bindingResult.hasErrors() || orderService.get(paymentDTO.getId()) == null){
            return "redirect:/payment_TEST?error";
        }
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("id", paymentDTO.getId());
        model.addAttribute("price", paymentDTO.getPrice());
        model.addAttribute("email", paymentDTO.getEmail());
        model.addAttribute("productName", paymentDTO.getProductName());
        return "payment/checkout";
    }

    @GetMapping("/success")
    public String success(String payment_intent, String payment_intent_client_secret, String redirect_status)
    {

        Order o = orderService.getByIntent(payment_intent);

        if(o == null) return "redirect:/";

        if(!Objects.equals(o.getPaymentIntentClientSecret(), payment_intent_client_secret) || !Objects.equals(redirect_status, "succeeded"))
        {
            o.setStatus(OrderStatusEnum.CANCELLED);
            orderService.save(o);
            try {
                emailService.sendHtmlEmail(
                        "New order status",
                        "<p>Your order #"+o.getId()+" now has new status: "+OrderStatusEnum.CANCELLED+"</p>",
                        o.getCustomer().getUsername(),
                        o.getCustomer().getEmail()
                );
            } catch (MessagingException e) {
                logger.error("Error on messaging to"+o.getCustomer().getEmail()+"\nError info:"+e.getMessage());
            }
        }
        else
        {
            o.setStatus(OrderStatusEnum.SHIPPING);
            orderService.save(o);
            for(OrderedProduct product : o.getItems())
            {
                Product p = productService.get(product.getId());
                p.setQuantity(p.getQuantity()-product.getQuantity());
                productService.save(p);
            }

            try {
                emailService.sendHtmlEmail(
                    "New order status",
                    "<p>Your order #"+o.getId()+" now has new status: "+OrderStatusEnum.SHIPPING+"</p>",
                    o.getCustomer().getUsername(),
                    o.getCustomer().getEmail()
                );
            } catch (MessagingException e) {
                logger.error("Error on messaging to"+o.getCustomer().getEmail()+"\nError info:"+e.getMessage());
            }

        }
        return "redirect:/user/order?id="+o.getId();
    }
}
