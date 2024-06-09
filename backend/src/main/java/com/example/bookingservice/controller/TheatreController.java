package com.example.bookingservice.controller;

import com.example.bookingservice.dto.CityRes;
import com.example.bookingservice.dto.MovieTheatreSearchRes;
import com.example.bookingservice.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/movie/{movieId}/timing")
    public List<MovieTheatreSearchRes> getMovieTheatreSearchRes(@PathVariable String movieId){
        return theatreService.getTheatreInformationByMovieId(movieId);
    }
}
