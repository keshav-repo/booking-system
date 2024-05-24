package com.example.bookingservice.controller;

import com.example.bookingservice.dto.MovieDetailRes;
import com.example.bookingservice.dto.MovieShortDetailRes;
import com.example.bookingservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/list/{city}")
    public List<MovieShortDetailRes> getMoviesInaCity(@PathVariable String city){
        return movieService.getMovies(city);
    }
    @GetMapping("/details/{movieId}")
    public MovieDetailRes getMovieDetails(@PathVariable String movieId){
        return movieService.getMoviesDetails(movieId);
    }


}
