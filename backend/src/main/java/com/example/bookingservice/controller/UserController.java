package com.example.bookingservice.controller;

import com.example.bookingservice.dto.LoginReq;
import com.example.bookingservice.dto.UserRegistrationReq;
import com.example.bookingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginReq loginReq) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUserName(), loginReq.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw badCredentialsException;
        }
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserRegistrationReq userRegistrationReq){
        userService.addUser(userRegistrationReq);
        return ResponseEntity.accepted().build();
    }
}
