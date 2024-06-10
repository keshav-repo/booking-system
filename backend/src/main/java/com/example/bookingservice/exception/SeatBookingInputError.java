package com.example.bookingservice.exception;

public class SeatBookingInputError extends BaseException{
    public SeatBookingInputError(String message, String code) {
        super(message, code);
    }
}
