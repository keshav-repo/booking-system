package com.example.bookingservice.service;

import com.example.bookingservice.dto.*;
import com.example.bookingservice.entity.SeatType;
import com.example.bookingservice.entity.TheatreEntity;

import java.math.BigDecimal;
import java.util.List;

public interface TheatreService {
    /**
     * Get list of cities
     * @return
     */
    public List<CityRes> getCityList();

    /**
     * Get information about theatre and movie timing corresponding to a movie
     * @param movieId
     * @return
     */
    public List<MovieTheatreSearchRes> getTheatreInformationByMovieId(String movieId);

    /**
     * Add a theatre
     * @param theatreReq
     * @return
     */
    public TheatreRes addTheatre(TheatreReq theatreReq);

    /**
     * add a screen to theatre
     * @param screenReq
     * @return
     */
    public ScreenRes addScreen(ScreenReq screenReq);

    /**
     *
     * @param screenId
     * @return
     */
    public List<SeatRes> getSeats(int screenId);

    /**
     * Add a seat to a screen
     * @param seatReq
     * @return
     */
    public SeatRes addSeat(SeatReq seatReq);

    /**
     * Get pricing for a particular seat type and theatre
     * @param seatType
     * @param theatreEntity
     * @return
     */
    BigDecimal getPrice(SeatType seatType, TheatreEntity theatreEntity);
}
