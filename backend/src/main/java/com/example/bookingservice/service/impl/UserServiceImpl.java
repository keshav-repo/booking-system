package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.UserRegistrationReq;
import com.example.bookingservice.entity.User;
import com.example.bookingservice.repo.UserRepo;
import com.example.bookingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findByUserName(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found " + username);
        }
        User user = optionalUser.get();
        StringBuilder stringBuilder = new StringBuilder(user.getPassword());
        return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password(new String(stringBuilder))
                .build();
    }


    @Override
    public void addUser(UserRegistrationReq userRegistrationReq) {
        Optional<User> optionalUser = userRepo.findByUserName(userRegistrationReq.getUserName());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("User already present");
        }
        String hashedPassword = passwordEncoder.encode(userRegistrationReq.getPassword());
        User user = User.builder()
                .userName(userRegistrationReq.getUserName())
                .password(hashedPassword)
                .build();
        userRepo.save(user);
    }
}
