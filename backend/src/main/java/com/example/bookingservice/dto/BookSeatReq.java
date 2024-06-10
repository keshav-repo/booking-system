package com.example.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSeatReq {
    private int theatreId;
    private int movieId;
    private List<Integer> seatIds;
    private LocalDateTime movieStartTiming;
}
