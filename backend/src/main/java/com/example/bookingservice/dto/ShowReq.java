package com.example.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowReq {
    private int screenId;
    private int theatreId;
    private int movieId;
    private LocalDateTime movieStartTiming;
    private LocalDateTime movieEndTiming;
}
