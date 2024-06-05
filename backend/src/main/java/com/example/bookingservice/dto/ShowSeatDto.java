package com.example.bookingservice.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSeatDto {
    private int showId;
    private int theatreId;

    
    private int showSeatId;
    private int row;
    private int col;
    private String seatName;
    private String seatType;
    private boolean isAvailable;
}
