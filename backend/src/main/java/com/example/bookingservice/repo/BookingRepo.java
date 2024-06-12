package com.example.bookingservice.repo;

import com.example.bookingservice.entity.BookingEntity;
import com.example.bookingservice.entity.BookingStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends JpaRepository<BookingEntity, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE BookingEntity b SET b.bookingStatus = :status WHERE b.bookingId = :bookingId")
    void updateBookingStatus(@Param("bookingId") int bookingId, @Param("status") BookingStatus status);

}
