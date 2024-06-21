package com.example.bookingservice.service.impl;

import com.example.bookingservice.config.Constants;
import com.example.bookingservice.dto.*;
import com.example.bookingservice.entity.*;
import com.example.bookingservice.exception.*;
import com.example.bookingservice.repo.*;
import com.example.bookingservice.service.ShowService;
import com.example.bookingservice.service.TheatreService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${booking.ttl.inMin}")
    private long bookingTtl;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    @Override
    public ShowRes addShow(ShowReq showReq) {
        MovieEntity movieEntity = movieRepository.findById(showReq.getMovieId())
                .orElseThrow(() -> new MovieNotFound(ErrorCode.MOVIE_NOT_FOUND.getMessage(), ErrorCode.SHOW_CONFLICT.getCode()));
        TheatreEntity theatreEntity = theatreRepo.findById(showReq.getTheatreId())
                .orElseThrow(() -> new TheatreNotFound(ErrorCode.THEATRE_NOT_FOUND.getMessage(), ErrorCode.THEATRE_NOT_FOUND.getCode()));
        ScreenEntity screenEntity = screenRepo.findById(showReq.getScreenId())
                .orElseThrow(() -> new ScreenNotFound(ErrorCode.SCREEN_NOT_FOUND.getMessage(), ErrorCode.SCREEN_NOT_FOUND.getCode()));

        boolean isConflict = showRepo.hasConflictingShows(showReq.getTheatreId(), showReq.getScreenId(), showReq.getMovieId(), showReq.getMovieStartTiming(), showReq.getMovieEndTiming());
        if (isConflict) {
            throw new ShowConflictException(ErrorCode.SHOW_CONFLICT.getMessage(), ErrorCode.SHOW_CONFLICT.getCode());
        }
        ShowEntity showEntity = ShowEntity.builder()
                .movieEntity(movieEntity)
                .screenEntity(screenEntity)
                .theatreEntity(theatreEntity)
                .movieStartTiming(showReq.getMovieStartTiming())
                .movieEndTiming(showReq.getMovieEndTiming())
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
    public List<ShowSeatDto> getSeatsForShow(int theatreId, LocalDateTime movieTiming, int movieId) {

        List<ShowEntity> showEntityList = showRepo.findShows(theatreId, movieTiming, movieId, true);
        if (showEntityList.size() == 0) {
            // throw error
        }
        // we will assume that only one screen will play that movie at that time
        ShowEntity showEntity = showEntityList.get(0);
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

    @Override
    @Transactional
    public BookingDto bookSeat(BookSeatReq bookSeatReq) {
        Optional<ShowEntity> showEntityOptional = showRepo.findByMovieEntityMovieIdAndTheatreEntityTheatreIdAndMovieStartTiming(bookSeatReq.getMovieId(), bookSeatReq.getTheatreId(), bookSeatReq.getMovieStartTiming());
        if (!showEntityOptional.isPresent()) {
            throw new ShowNotFound(ErrorCode.SHOW_NOT_FOUND.getMessage(), ErrorCode.SHOW_NOT_FOUND.getCode());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user =  userRepo.findByUserName(userName).get();

        List<ShowSeat> showSeatList = showSeatList = showSeatRepo.findAllById(bookSeatReq.getSeatIds());
        if (showSeatList.size() != bookSeatReq.getSeatIds().size()) {
            throw new SeatBookingInputError("wrong seat are passed", ErrorCode.SEAT_BOOKING_INPUT_ERROR.getCode());
        }
        for (ShowSeat showSeat : showSeatList) {
            if (!showSeat.isAvailable()) {
                throw new SeatBookingInputError("Given seat Id" + showSeat.getSeat().getSeatId() + " is not available", ErrorCode.SEAT_BOOKING_INPUT_ERROR.getCode());
            }
            showSeat.setAvailable(false);
        }
        try {
            showSeatRepo.saveAll(showSeatList);
        } catch (OptimisticLockingFailureException e) {
            log.info("Failed to book the seat due to concurrent modification " + e.getMessage());
            e.printStackTrace();
            throw new SeatBookingInternalError("Failed to book the seat due to concurrent modification", ErrorCode.SEAT_BOOKING_ERROR.getCode());
        } catch (Exception e) {
            log.error("error reserving seat information");
            e.printStackTrace();
            throw new SeatBookingInternalError("error reserving seat information", ErrorCode.SEAT_BOOKING_ERROR.getCode());
        }

        BookingEntity bookingEntity = BookingEntity.builder()
                .bookingTime(LocalDateTime.now())
                .showSeatList(showSeatList)
                .showEntity(showEntityOptional.get())
                .bookingStatus(BookingStatus.CREATED)
                .user(user)
                .build();

        try {
            bookingRepo.save(bookingEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeatBookingInternalError("Error saving booking information", ErrorCode.SEAT_BOOKING_ERROR.getCode());
        }

        try {
            for (ShowSeat showSeat : showSeatList) {
                showSeat.setBookingEntity(bookingEntity);
            }
            showSeatRepo.saveAll(showSeatList);
        } catch (Exception e) {
            log.error("error reserving seat booking information");
            e.printStackTrace();
            throw new SeatBookingInternalError("error reserving seat booking information", ErrorCode.SEAT_BOOKING_ERROR.getCode());
        }

        try{
            BookingTTL bookingTTL = new BookingTTL(bookingEntity.getBookingId(), bookingEntity.getBookingTime());
            ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
            String key = String.format(Constants.TTL_BOOKING_KEY_FORMAT, Constants.TTL_BOOKING_PREFIX, bookingTTL.getBookingId());
            opsForValue.set(key, bookingTTL, Duration.ofMinutes(bookingTtl));
        }catch (Exception e){
            log.info("error saving booking info in redis");
            throw new SeatBookingInternalError("error saving booking info in redis", ErrorCode.SEAT_BOOKING_ERROR.getCode());
        }

        List<SeatRes> seatResList = showSeatList.stream().map(showSeat -> SeatRes.builder()
                        .seatId(showSeat.getSeat().getSeatId())
                        .row(showSeat.getSeat().getRow())
                        .col(showSeat.getSeat().getCol())
                        .seatName(showSeat.getSeat().getSeatName())
                        .seatType(showSeat.getSeat().getSeatType().name())
                        .build())
                .collect(Collectors.toList());

        BookingDto bookingDto = BookingDto.builder()
                .bookingId(bookingEntity.getBookingId())
                .bookedSeat(seatResList)
                .bookingTime(bookingEntity.getBookingTime())
                .movieId(showEntityOptional.get().getMovieEntity().getMovieId())
                .theatreId(showEntityOptional.get().getTheatreEntity().getTheatreId())
                .build();

        return bookingDto;
    }
}
