package com.example.bookingservice.controller;

import com.example.bookingservice.dto.ShowReq;
import com.example.bookingservice.dto.ShowRes;
import com.example.bookingservice.dto.ShowSeatDto;
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

    @PostMapping
    public ShowRes addShow(@RequestBody ShowReq showReq){
        return showService.addShow(showReq);
    }

    @GetMapping("/seats")
    public List<ShowSeatDto> seatsForAShow(@RequestParam Integer theatreId,@RequestParam String localDateTime, @RequestParam int movieId){

        // Define the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return showService.getSeatsForShow(theatreId,  LocalDateTime.parse(localDateTime, formatter), movieId);
    }
}
