package com.example.bookingservice.exception;

public class TheatreNotFound extends BaseException{
    public TheatreNotFound(String message, String code) {
        super(message, code);
    }
}
