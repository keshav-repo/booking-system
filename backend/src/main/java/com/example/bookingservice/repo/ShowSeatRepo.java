package com.example.bookingservice.repo;

import com.example.bookingservice.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSeatRepo extends JpaRepository<ShowSeat,Integer> {
}
