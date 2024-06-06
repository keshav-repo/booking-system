package com.example.bookingservice.service;

import com.example.bookingservice.dto.UserRegistrationReq;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public void addUser(UserRegistrationReq userRegistrationReq);

}
