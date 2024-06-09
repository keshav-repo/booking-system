package com.example.bookingservice.controller;

import com.example.bookingservice.dto.ShowReq;
import com.example.bookingservice.dto.ShowRes;
import com.example.bookingservice.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/show")
public class AdminShowController {
    @Autowired
    private ShowService showService;

    @PostMapping
    public ShowRes addShow(@RequestBody ShowReq showReq){
        return showService.addShow(showReq);
    }

}
