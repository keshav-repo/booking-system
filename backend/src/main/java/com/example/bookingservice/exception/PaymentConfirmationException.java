package com.example.bookingservice.exception;

public class PaymentConfirmationException  extends BaseException{
    public PaymentConfirmationException(String message, String code) {
        super(message, code);
    }
}
