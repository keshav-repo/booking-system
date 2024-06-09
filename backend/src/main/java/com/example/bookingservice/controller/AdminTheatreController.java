package com.example.bookingservice.controller;

import com.example.bookingservice.dto.*;
import com.example.bookingservice.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/theatre")
public class AdminTheatreController {
    @Autowired
    private TheatreService theatreService;

    @PostMapping
    public TheatreRes addTheatre(@RequestBody TheatreReq theatreReq){
        return theatreService.addTheatre(theatreReq);
    }

    @PostMapping("/admin/screen")
    public ScreenRes addScreen(@RequestBody ScreenReq screenReq){
        return theatreService.addScreen(screenReq);
    }

    @PostMapping("/admin/screen/seat")
    public SeatRes addSeat(@RequestBody SeatReq seatReq){
        return theatreService.addSeat(seatReq);
    }

    @GetMapping("/admin/screen/seat/{screenId}")
    public List<SeatRes> getSeatScreen(@PathVariable Integer screenId){
        return theatreService.getSeats(screenId);
    }

}
