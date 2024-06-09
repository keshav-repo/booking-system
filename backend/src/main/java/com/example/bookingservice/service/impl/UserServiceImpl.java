package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.UserRegistrationReq;
import com.example.bookingservice.entity.AuthorityEntity;
import com.example.bookingservice.entity.Role;
import com.example.bookingservice.entity.User;
import com.example.bookingservice.repo.AuthorityRepo;
import com.example.bookingservice.repo.UserRepo;
import com.example.bookingservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthorityRepo authorityRepo;
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

        List<GrantedAuthority> grantedAuthorityList = user.getAuthorities().stream()
                .map(a-> new SimpleGrantedAuthority(a.getRole().name()))
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder(user.getPassword());
        return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password(new String(stringBuilder))
                .authorities(grantedAuthorityList)
                .roles(user.getAuthorities().stream().map(u -> u.getRole().name()).collect(Collectors.toList()).toArray(new String[0]))
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

        if (userRegistrationReq.getRoles().isEmpty()) {
            AuthorityEntity userRole = authorityRepo.findByRole(Role.USER);
            user.setAuthorities(List.of(userRole));
        } else {
            user.setAuthorities(userRegistrationReq.getRoles().stream()
                    .map(r -> authorityRepo.findByRole(Role.valueOf(r)))
                    .collect(Collectors.toList()));
        }
        try{
            userRepo.save(user);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("error in saving new user ");
        }
    }
}
