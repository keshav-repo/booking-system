package com.example.bookingservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDto {
    private int bookingId;
    private LocalDateTime bookingTime;
    private List<SeatRes> bookedSeat;
    private int movieId;
    private int theatreId;
}
