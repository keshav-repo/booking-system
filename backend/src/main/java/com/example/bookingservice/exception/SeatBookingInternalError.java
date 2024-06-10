package com.example.bookingservice.exception;

public class SeatBookingInternalError extends BaseException{
    public SeatBookingInternalError(String message, String code) {
        super(message, code);
    }
}
