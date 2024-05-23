package com.example.bookingservice.service;

public interface MovieService {
    /**
     * Get list of movie from a city
     * @param city
     */
    public void getMovies(String city);

    /**
     * Get city list
     */
    public void getCityList();


}
