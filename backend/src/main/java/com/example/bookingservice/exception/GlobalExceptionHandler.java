package com.example.bookingservice.exception;

import com.example.bookingservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ShowConflictException.class})
    public ResponseEntity<ErrorResponse> handleConflictException(BaseException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler({SeatBookingInternalError.class})
    public ResponseEntity<ErrorResponse> handleInternalException(BaseException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({SeatBookingInputError.class})
    public ResponseEntity<ErrorResponse> handleInputErrorException(BaseException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({MovieNotFound.class, ScreenNotFound.class, TheatreNotFound.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(BaseException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.NOT_FOUND);
    }
}