package com.example.bookingservice.service;

import com.example.bookingservice.dto.ShowReq;
import com.example.bookingservice.dto.ShowRes;

public interface ShowService {
    public ShowRes addShow(ShowReq showReq);
}
