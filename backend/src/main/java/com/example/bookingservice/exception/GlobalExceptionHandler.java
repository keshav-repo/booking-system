package com.example.bookingservice.exception;

import com.example.bookingservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShowConflictException.class)
    public ResponseEntity<ErrorResponse> handleShowConflictException(BaseException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({MovieNotFound.class, ScreenNotFound.class, TheatreNotFound.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(BaseException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.NOT_FOUND);
    }
}