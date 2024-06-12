package com.example.bookingservice.filter;

import com.example.bookingservice.entity.BookingEntity;
import com.example.bookingservice.entity.BookingStatus;
import com.example.bookingservice.entity.ShowSeat;
import com.example.bookingservice.exception.ErrorCode;
import com.example.bookingservice.exception.RedisTTLException;
import com.example.bookingservice.repo.BookingRepo;
import com.example.bookingservice.repo.ShowSeatRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RedisKeyExpiredListener implements MessageListener {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private ShowSeatRepo showSeatRepo;

    @Transactional
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        log.info("expired ket {}", expiredKey);
        try {
            int bookingId = Integer.valueOf(expiredKey.split(":")[1]);
            bookingRepo.updateBookingStatus(bookingId, BookingStatus.TIME_LIMIT_EXCEED);

            BookingEntity bookingEntity = bookingRepo.findById(bookingId).get();
            List<ShowSeat> showSeatList = bookingEntity.getShowSeatList();
            for (ShowSeat showSeat : showSeatList) {
                showSeat.setAvailable(true);
                showSeat.setBookingEntity(null);
            }
            showSeatRepo.saveAll(showSeatList);
        } catch (Exception e) {
            log.error("error updating ttl for booking with redis key: {}", expiredKey);
            e.printStackTrace();
            throw new RedisTTLException("error updating ttl for booking with redis key: " + expiredKey, ErrorCode.REDIS_TTL_ERROR.getCode());
        }
    }
}
