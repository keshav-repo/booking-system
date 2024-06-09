package com.example.bookingservice.controller;

import com.example.bookingservice.dto.LoginReq;
import com.example.bookingservice.dto.UserRegistrationReq;
import com.example.bookingservice.helper.JwtHelper;
import com.example.bookingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginReq loginReq) {
        Authentication authentication = null;
        try {
             authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUserName(), loginReq.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw badCredentialsException;
        }
        List<String> roles = authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toList());
        String token = jwtHelper.generateToken(loginReq.getUserName(), roles);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserRegistrationReq userRegistrationReq){
        userService.addUser(userRegistrationReq);
        return ResponseEntity.accepted().build();
    }
}
