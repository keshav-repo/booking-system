package com.example.bookingservice.service.impl;

import com.example.bookingservice.config.Constants;
import com.example.bookingservice.dto.PaymentConfirmDto;
import com.example.bookingservice.dto.SeatRes;
import com.example.bookingservice.dto.TicketRes;
import com.example.bookingservice.entity.*;
import com.example.bookingservice.exception.BookingNotFound;
import com.example.bookingservice.exception.ErrorCode;
import com.example.bookingservice.exception.PaymentAlreadyDone;
import com.example.bookingservice.exception.PaymentInternalException;
import com.example.bookingservice.repo.BookingRepo;
import com.example.bookingservice.repo.PaymentRepo;
import com.example.bookingservice.repo.ShowSeatRepo;
import com.example.bookingservice.repo.TicketRepo;
import com.example.bookingservice.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ShowSeatRepo showSeatRepo;

    @Transactional
    @Override
    public TicketRes paymentConfirmation(PaymentConfirmDto paymentConfirmDto) {
        Optional<BookingEntity> bookingEntityOptional = bookingRepo.findById(paymentConfirmDto.getBookingId());
        if (!bookingEntityOptional.isPresent()) {
            throw new BookingNotFound(ErrorCode.BOOKING_NOT_FOUND.getMessage(), ErrorCode.BOOKING_NOT_FOUND.getCode());
        }
        BookingEntity bookingEntity = bookingEntityOptional.get();

        if(bookingEntity.getBookingStatus().equals(BookingStatus.CONFIRMED)){
            throw new PaymentAlreadyDone(ErrorCode.PAYMENT_ALREADY_DONE.getMessage(), ErrorCode.PAYMENT_ALREADY_DONE.getCode());
        }

        // create payment entity
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .paymentStatus(PaymentStatus.CONFIRMED)
                .bookingEntity(bookingEntity)
                .paymentId(paymentConfirmDto.getPaymentId())
                .build();
        try {
            paymentRepo.save(paymentEntity);
        } catch (Exception e) {
            log.error("error saving payment entity");
            throw new PaymentInternalException(ErrorCode.PAYMENT_INTERNAL_EXCEPTION.getMessage(), ErrorCode.PAYMENT_INTERNAL_EXCEPTION.getCode());
        }

        ShowEntity showEntity = bookingEntity.getShowEntity();
        MovieEntity movieEntity = showEntity.getMovieEntity();
        List<ShowSeat> showSeatList = bookingEntity.getShowSeatList();

        // create ticket entity
        TicketEntity ticketEntity = TicketEntity.builder()
                .bookingTime(LocalDateTime.now())
                .movieEntity(movieEntity)
                .showEntity(showEntity)
                .build();

        try {
            ticketRepo.save(ticketEntity);
        } catch (Exception e) {
            log.error("error saving ticket entity");
            throw new PaymentInternalException(ErrorCode.PAYMENT_INTERNAL_EXCEPTION.getMessage(), ErrorCode.PAYMENT_INTERNAL_EXCEPTION.getCode());
        }

        try{
             for(ShowSeat showSeat: showSeatList){
                 showSeat.setBookingEntity(bookingEntity);
                 showSeat.setTicketEntity(ticketEntity);
             }
            showSeatRepo.saveAll(showSeatList);
        }catch (Exception e){
            log.error("error saving ticket information corresponding to a show seat");
            throw new PaymentInternalException(ErrorCode.PAYMENT_INTERNAL_EXCEPTION.getMessage(), ErrorCode.PAYMENT_INTERNAL_EXCEPTION.getCode());
        }

        // set booking status confirmed
        bookingEntity.setBookingStatus(BookingStatus.CONFIRMED);
        try{
            bookingRepo.save(bookingEntity);
        }catch (Exception e){
            log.error("error saving booking status to confirm");
            throw new PaymentInternalException(ErrorCode.PAYMENT_INTERNAL_EXCEPTION.getMessage(), ErrorCode.PAYMENT_INTERNAL_EXCEPTION.getCode());
        }

        String key = String.format(Constants.TTL_BOOKING_KEY_FORMAT, Constants.TTL_BOOKING_PREFIX, bookingEntity.getBookingId());
        // remove key from redis
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("error deleting redis key for ttl");
        }

        TicketRes ticketRes = TicketRes.builder()
                .bookingTime(bookingEntity.getBookingTime())
                .movieName(movieEntity.getName())
                .screenName(showEntity.getScreenEntity().getScreenName())
                .theatreName(showEntity.getTheatreEntity().getName())
                .theatreCity(showEntity.getTheatreEntity().getCity())
                .addressLine(showEntity.getTheatreEntity().getAddressLine1())
                .bookedSeats(showSeatList.stream().map(showSeat -> SeatRes.builder()
                        .seatId(showSeat.getShowSeatId())
                        .col(showSeat.getSeat().getCol())
                        .row(showSeat.getSeat().getRow())
                        .seatName(showSeat.getSeat().getSeatName())
                        .seatType(showSeat.getSeat().getSeatType().name())
                        .build()).collect(Collectors.toList()))
                .build();

        return ticketRes;
    }
}
