package com.example.bookingservice.exception;

public class PaymentAlreadyDone extends BaseException{
    public PaymentAlreadyDone(String message, String code) {
        super(message, code);
    }
}
