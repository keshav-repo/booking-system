package com.example.bookingservice.controller;

import com.example.bookingservice.dto.*;
import com.example.bookingservice.service.TheatreService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @GetMapping("/cities")
    public List<CityRes> getCityList(){
        return theatreService.getCityList();
    }

    @GetMapping("/timing/{movieId}")
    public List<MovieTheatreSearchRes> getMovieTheatreSearchRes(@PathVariable String movieId){
        return theatreService.getTheatreInformationByMovieId(movieId);
    }
    @PostMapping
    public TheatreRes addTheatre(@RequestBody TheatreReq theatreReq){
        return theatreService.addTheatre(theatreReq);
    }

    @PostMapping("/screen")
    public ScreenRes addScreen(@RequestBody ScreenReq screenReq){
        return theatreService.addScreen(screenReq);
    }

    @PostMapping("/screen/seat")
    public SeatRes addSeat(@RequestBody SeatReq seatReq){
        return theatreService.addSeat(seatReq);
    }

    @GetMapping("/screen/seat/{screenId}")
    public List<SeatRes> getSeatScreen(@PathVariable Integer screenId){
        return theatreService.getSeats(screenId);
    }

}
