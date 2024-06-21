package com.example.bookingservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketRes {
    private LocalDateTime bookingTime;
    private String movieName;
    private String screenName;
    private String theatreName;
    private String theatreCity;
    private String addressLine;
    private List<SeatRes> bookedSeats;
}
