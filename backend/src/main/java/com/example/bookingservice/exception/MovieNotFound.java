package com.example.bookingservice.exception;

public class MovieNotFound extends BaseException {
    public MovieNotFound(String message, String code) {
        super(message, code);
    }
}
