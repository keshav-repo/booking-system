package com.example.bookingservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SEAT_BOOKING_INPUT_ERROR("ERR07", "Book Seat input error"),
    SHOW_NOT_FOUND("ERR06", "Seat not found"),
    SEAT_BOOKING_ERROR("ERR05", "Seat booking error"),
    SCREEN_NOT_FOUND("ERR04","Screen not found"),
    THEATRE_NOT_FOUND("ERR03","Theatre not found"),
    MOVIE_NOT_FOUND("ERR02", "Movie with the specified ID not found."),
    SHOW_CONFLICT("ERR01", "Conflicting show time detected."),
    INTERNAL_SERVER_ERROR("ERR00", "An unexpected error occurred.");


    private final String code;
    private final String message;
}
