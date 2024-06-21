package com.example.bookingservice.controller;

import com.example.bookingservice.dto.PaymentConfirmDto;
import com.example.bookingservice.dto.TicketRes;
import com.example.bookingservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/conform")
    public TicketRes confirm(@RequestBody PaymentConfirmDto paymentConfirmDto){
       return paymentService.paymentConfirmation(paymentConfirmDto);
    }
}
