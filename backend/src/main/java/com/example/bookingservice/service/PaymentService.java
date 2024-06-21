package com.example.bookingservice.service;

import com.example.bookingservice.dto.PaymentConfirmDto;
import com.example.bookingservice.dto.TicketRes;

public interface PaymentService {
    public TicketRes paymentConfirmation(PaymentConfirmDto paymentConfirmDto);
}
