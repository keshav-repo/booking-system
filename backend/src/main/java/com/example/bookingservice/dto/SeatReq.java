package com.example.bookingservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatReq {
    private int row;
    private int col;
    private String seatName;
    private String seatType;
    private int screenId;
}
