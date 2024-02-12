package com.example.fishop.controller;

import com.example.fishop.dto.PaymentDTO;
import com.example.fishop.dto.response.ResponsePaymentDTO;
import com.example.fishop.entity.Order;
import com.example.fishop.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class PaymentRestController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentDTO request)
            throws StripeException {
        Order o = orderService.get(request.getId());
        if(o == null || !Objects.equals(request.getEmail(), SecurityContextHolder.getContext().getAuthentication().getName())) return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();

        PaymentIntentCreateParams params =
            PaymentIntentCreateParams.builder()
                .setAmount((long) (request.getPrice() * 100L))
                .putMetadata("productName",
                    request.getProductName())
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                    PaymentIntentCreateParams
                        .AutomaticPaymentMethods
                        .builder()
                        .setEnabled(true)
                        .build()
                )
                .build();

        PaymentIntent intent =
            PaymentIntent.create(params);

        o.setPaymentIntent(intent.getId());
        o.setPaymentIntentClientSecret(intent.getClientSecret());
        orderService.save(o);


        return ResponseEntity.ok(new ResponsePaymentDTO(intent.getId(),
                intent.getClientSecret()));
    }
}
