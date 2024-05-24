package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.*;
import com.example.bookingservice.entity.*;
import com.example.bookingservice.repo.ScreenRepo;
import com.example.bookingservice.repo.SeatRepo;
import com.example.bookingservice.repo.ShowRepo;
import com.example.bookingservice.repo.TheatreRepo;
import com.example.bookingservice.service.TheatreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TheatreServiceImpl implements TheatreService {
    @Autowired
    private TheatreRepo theatreRepo;
    @Autowired
    private ShowRepo showRepo;
    @Autowired
    private ScreenRepo screenRepo;
    @Autowired
    private SeatRepo seatRepo;
    @Override
    public List<CityRes> getCityList() {
        List<String> cityList = theatreRepo.findDistinctCities();
        return cityList.stream().map(city -> new CityRes(city)).collect(Collectors.toList());
    }

    @Override
    public List<MovieTheatreSearchRes> getTheatreInformationByMovieId(String movieId) {
        List<ShowEntity> showEntries = showRepo.findByMovieId(Integer.valueOf(movieId));
        return showEntries.stream()
                .collect(Collectors.groupingBy(
                        ShowEntity::getTheatreEntity,
                        Collectors.mapping(ShowEntity::getMovieTiming, Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> MovieTheatreSearchRes.builder()
                        .movieTimings(entry.getValue())
                        .theatreId(entry.getKey().getTheatreId())
                        .theatreName(entry.getKey().getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public TheatreRes addTheatre(TheatreReq theatreReq) {
        TheatreEntity theatreEntity = TheatreEntity.builder()
                .name(theatreReq.getName())
                .city(theatreReq.getCity())
                .addressLine1(theatreReq.getAddressLine1())
                .country(theatreReq.getCountry())
                .state(theatreReq.getState())
                .pin(theatreReq.getPin())
                .build();

        theatreRepo.save(theatreEntity);

        return TheatreRes.builder()
                .theatreId(theatreEntity.getTheatreId())
                .addressLine1(theatreReq.getAddressLine1())
                .city(theatreEntity.getCity())
                .country(theatreEntity.getCountry())
                .state(theatreEntity.getState())
                .pin(theatreEntity.getPin())
                .name(theatreEntity.getName())
                .build();
    }

    @Override
    public ScreenRes addScreen(ScreenReq screenReq) {
        TheatreEntity theatreEntity = theatreRepo.findById(screenReq.getTheatreId()).get();
        // TODO: check if there is no theatre corresponding to theatreId
        ScreenEntity screenEntity = ScreenEntity.builder()
                .screenName(screenReq.getScreenName())
                .theatreEntity(theatreEntity)
                .build();
        try {
            screenRepo.save(screenEntity);
        } catch (Exception e) {
            log.error("error adding screen info {}", e.getMessage());
        }
        return ScreenRes.builder()
                .screenId(screenEntity.getScreenId())
                .screenName(screenEntity.getScreenName())
                .build();
    }

    @Override
    public List<SeatRes> getSeats(int screenId) {
        ScreenEntity screenEntity = screenRepo.findById(screenId).get();
        List<Seat> seatList = screenEntity.getSeatList();
        return seatList.stream()
                .map((s)-> SeatRes.builder()
                        .seatId(s.getSeatId())
                        .col(s.getCol())
                        .row(s.getRow())
                        .seatName(s.getSeatName())
                        .seatType(s.getSeatType().name())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public SeatRes addSeat(SeatReq seatReq) {
        ScreenEntity screenEntity = screenRepo.findById(seatReq.getScreenId()).get();

        Seat seat = Seat.builder().seatName(seatReq.getSeatName())
                .col(seatReq.getCol()).row(seatReq.getRow())
                .seatType(SeatType.valueOf(seatReq.getSeatType()))
                .screenEntity(screenEntity)
                .build();

        seatRepo.save(seat);

        return SeatRes.builder()
                .seatType(seat.getSeatType().name())
                .seatName(seat.getSeatName())
                .row(seat.getRow())
                .col(seat.getCol())
                .seatId(seat.getSeatId())
                .build();
    }
}
