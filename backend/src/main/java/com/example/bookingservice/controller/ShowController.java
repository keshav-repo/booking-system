package com.example.bookingservice.controller;

import com.example.bookingservice.dto.*;
import com.example.bookingservice.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @GetMapping("/seats")
    public List<ShowSeatDto> seatsForAShow(@RequestParam Integer theatreId,@RequestParam String localDateTime, @RequestParam int movieId){
        // Define the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return showService.getSeatsForShow(theatreId,  LocalDateTime.parse(localDateTime, formatter), movieId);
    }

    @PostMapping("/book")
    public BookingDto bookSeat(@RequestBody BookSeatReq bookSeatReq){
        return showService.bookSeat(bookSeatReq);
    }
}
