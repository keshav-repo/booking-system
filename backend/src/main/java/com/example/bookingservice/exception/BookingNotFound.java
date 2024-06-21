package com.example.bookingservice.exception;

public class BookingNotFound extends BaseException{
    public BookingNotFound(String message, String code) {
        super(message, code);
    }
}
