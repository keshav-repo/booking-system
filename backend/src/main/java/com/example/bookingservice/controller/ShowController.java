package com.example.bookingservice.controller;

import com.example.bookingservice.dto.ShowReq;
import com.example.bookingservice.dto.ShowRes;
import com.example.bookingservice.dto.ShowSeatDto;
import com.example.bookingservice.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/seats/{showId}")
    public List<ShowSeatDto> seatsForAShow(@PathVariable Integer showId){
        return showService.getSeatsForShow(showId);
    }
}
