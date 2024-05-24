package com.example.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieShortDetailRes {
    private String movieId;
    private String movieName;
    // this will also include attributes like movie logo, rating
}
