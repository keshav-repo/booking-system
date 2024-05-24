package com.example.bookingservice.repo;

import com.example.bookingservice.entity.PricingEntity;
import com.example.bookingservice.entity.SeatType;
import com.example.bookingservice.entity.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PricingRepository extends JpaRepository<PricingEntity, Integer> {
    Optional<PricingEntity> findBySeatTypeAndTheatreEntity(SeatType seatType, TheatreEntity theatreEntity);
}
