package com.example.fishop.controller;

import com.example.fishop.dto.PaymentDTO;
import com.example.fishop.dto.response.ResponsePaymentDTO;
import com.example.fishop.entity.Order;
import com.example.fishop.services.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentRestController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create-payment-intent")
    public ResponsePaymentDTO createPaymentIntent(@RequestBody PaymentDTO request)
            throws StripeException {
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

        Order o = orderService.get(request.getId());
        o.setPaymentIntent(intent.getId());
        o.setPaymentIntentClientSecret(intent.getClientSecret());
        orderService.save(o);


        return new ResponsePaymentDTO(intent.getId(),
                intent.getClientSecret());
    }
}
