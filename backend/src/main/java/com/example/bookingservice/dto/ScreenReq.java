package com.example.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScreenReq {
    private String name;
    private String screenName;
    private int theatreId;
}
