package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.ShowReq;
import com.example.bookingservice.dto.ShowRes;
import com.example.bookingservice.dto.ShowSeatDto;
import com.example.bookingservice.entity.*;
import com.example.bookingservice.repo.*;
import com.example.bookingservice.service.ShowService;
import com.example.bookingservice.service.TheatreService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private TheatreService theatreService;

    @Transactional
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

        List<Seat> seats = screenEntity.getSeatList();

        PricingEntity silverPricingEntity = pricingRepository.findBySeatTypeAndTheatreEntity(SeatType.SILVER, theatreEntity).get();
        PricingEntity goldPricingEntity = pricingRepository.findBySeatTypeAndTheatreEntity(SeatType.SILVER, theatreEntity).get();
        PricingEntity diamondPricingEntity = pricingRepository.findBySeatTypeAndTheatreEntity(SeatType.SILVER, theatreEntity).get();

        List<ShowSeat> showSeatList = new ArrayList<>();
        for (Seat seat : seats) {
            ShowSeat showSeat = ShowSeat.builder()
                    .seat(seat)
                    .showEntity(showEntity)
                    .isAvailable(true)
                    .build();
            switch (seat.getSeatType()) {
                case SILVER:
                    showSeat.setPrice(silverPricingEntity.getPrice());
                    break;
                case DIAMOND:
                    showSeat.setPrice(diamondPricingEntity.getPrice());
                    break;
                case GOLD:
                    showSeat.setPrice(goldPricingEntity.getPrice());
            }
            showSeatList.add(showSeat);
        }

        showSeatRepo.saveAll(showSeatList);

        return ShowRes.builder().showId(showEntity.getShowId()).build();
    }

    @Override
    public List<ShowSeatDto> getSeatsForShow(int showId) {
        Optional<ShowEntity> showEntityOptional = showRepo.findById(showId);
        if (!showEntityOptional.isPresent()) {
            // throw error
        }
        ShowEntity showEntity = showEntityOptional.get();
        return showEntity.getSeatList()
                .stream().map(showSeat -> ShowSeatDto.builder()
                        .seatName(showSeat.getSeat().getSeatName())
                        .showSeatId(showSeat.getShowSeatId())
                        .seatType(showSeat.getSeat().getSeatType().name())
                        .row(showSeat.getSeat().getRow())
                        .col(showSeat.getSeat().getCol())
                        .isAvailable(showSeat.isAvailable())
                        .build())
                .collect(Collectors.toList());
    }
}