package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.ShowReq;
import com.example.bookingservice.dto.ShowRes;
import com.example.bookingservice.entity.*;
import com.example.bookingservice.repo.*;
import com.example.bookingservice.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    private ShowRepo showRepo;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheatreRepo theatreRepo;
    @Autowired
    private ScreenRepo screenRepo;
    @Autowired
    private ShowSeatRepo showSeatRepo;

    @Autowired
    private PricingRepository pricingRepository;

    @Override
    public ShowRes addShow(ShowReq showReq) {
        // do validate for all ids if exist
        MovieEntity movieEntity = movieRepository.findById(showReq.getMovieId()).get();
        ScreenEntity screenEntity = screenRepo.findById(showReq.getScreenId()).get();
        TheatreEntity theatreEntity = theatreRepo.findById(showReq.getTheatreId()).get();

        ShowEntity showEntity = ShowEntity.builder()
                .movieEntity(movieEntity)
                .screenEntity(screenEntity)
                .theatreEntity(theatreEntity)
                .movieTiming(showReq.getMovieTiming())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        showRepo.save(showEntity);

       //  List<ShowSeat> showSeats = new LinkedList<>();
        List<Seat> seats = screenEntity.getSeatList();

        // PricingEntity pricingEntity =  pricingRepository.findBySeatTypeAndTheatreEntity(SeatType.SILVER, theatreEntity).get();

        List<ShowSeat> showSeats  = seats.stream().map(
                seat -> ShowSeat.builder()
                        .seat(seat)
                        .showEntity(showEntity)
                        .isAvailable(true)
                        .build()
        ).collect(Collectors.toList());

        showSeatRepo.saveAll(showSeats);

        return ShowRes.builder().showId(showEntity.getShowId()).build();
    }
}
