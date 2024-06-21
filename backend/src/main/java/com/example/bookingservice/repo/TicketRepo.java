package com.example.bookingservice.repo;

import com.example.bookingservice.entity.TicketEntity;
import com.example.bookingservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<TicketEntity, Integer> {
    List<TicketEntity> findByUser(User user);

   // Optional<ShowEntity> findByMovieEntityMovieIdAndTheatreEntityTheatreIdAndMovieStartTiming(Integer movieId, Integer theatreId, LocalDateTime movieStartTiming);

}
