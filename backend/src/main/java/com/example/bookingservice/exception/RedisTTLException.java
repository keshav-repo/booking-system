package com.example.bookingservice.exception;

public class RedisTTLException extends BaseException{
    public RedisTTLException(String message, String code) {
        super(message, code);
    }
}
