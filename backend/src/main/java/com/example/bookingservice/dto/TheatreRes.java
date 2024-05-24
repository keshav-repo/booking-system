package com.example.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TheatreRes extends TheatreReq{
    private int theatreId;
    @Builder
    public TheatreRes(String name, String city, String state, String country, String addressLine1, String pin,int theatreId) {
        super(name,city, state, country, addressLine1, pin);
        this.theatreId = theatreId;
    }
}
