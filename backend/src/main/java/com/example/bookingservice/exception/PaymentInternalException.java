package com.example.bookingservice.exception;

public class PaymentInternalException extends BaseException{
    public PaymentInternalException(String message, String code) {
        super(message, code);
    }
}
