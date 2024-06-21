package com.example.bookingservice.service;

import com.example.bookingservice.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowService {
    public ShowRes addShow(ShowReq showReq);

    /**
     * Get all the seats for a show. Seat can be available or not available
     */
    public List<ShowSeatDto> getSeatsForShow(int theatreId, LocalDateTime movieTiming, int movieId);

    /**
     * book seats using given list of seats
     */
    public BookingDto bookSeat(BookSeatReq bookSeatReq);

    public List<TicketRes> allTickets();
}
