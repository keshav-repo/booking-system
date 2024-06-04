package com.example.bookingservice.service;

import com.example.bookingservice.dto.ShowReq;
import com.example.bookingservice.dto.ShowRes;
import com.example.bookingservice.dto.ShowSeatDto;

import java.util.List;

public interface ShowService {
    public ShowRes addShow(ShowReq showReq);

    /**
     * Get all the seats for a show. Seat can be available or not available
     */
    public List<ShowSeatDto> getSeatsForShow(int showId);

    /**
     * book seats using given list of seats
     */
}
