package com.example.bookingservice.dto;

import com.example.bookingservice.entity.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatRes {
    private int seatId;
    private int row;
    private int col;
    private String seatName;
    private String seatType;
}
