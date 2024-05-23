package com.example.bookingservice.repo;

import com.example.bookingservice.entity.ShowEntry;
import com.example.bookingservice.entity.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepo extends JpaRepository<ShowEntry, Integer> {

    @Query("SELECT s FROM ShowEntry s INNER JOIN TheatreEntity t ON t.theatreId = s.theatreEntity.theatreId WHERE s.movieEntity.movieId = :movieId")
    List<ShowEntry> findByMovieId(@Param("movieId") int movieId);

}
