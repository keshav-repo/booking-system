package com.example.bookingservice.service;

import com.example.bookingservice.dto.MovieDetailRes;
import com.example.bookingservice.dto.MovieShortDetailRes;
import java.util.List;

public interface MovieService {
    /**
     * Get list of movie from a city
     * @param city
     */
    public List<MovieShortDetailRes> getMovies(String city);

    public MovieDetailRes getMoviesDetails(String movieId);

}
