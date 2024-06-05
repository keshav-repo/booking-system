package com.example.bookingservice.repo;

import com.example.bookingservice.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepo extends JpaRepository<ShowEntity, Integer> {

    @Query("SELECT s FROM ShowEntity s INNER JOIN TheatreEntity t ON t.theatreId = s.theatreEntity.theatreId WHERE s.movieEntity.movieId = :movieId")
    List<ShowEntity> findByMovieId(@Param("movieId") int movieId);

    @Query("SELECT s FROM ShowEntity s WHERE s.theatreEntity.theatreId = :theatreId AND s.movieTiming = :movieTiming AND s.movieEntity.movieId = :movieId AND s.movieEntity.isActive = :isActive")
    List<ShowEntity> findShows(@Param("theatreId") int theatreId,
                               @Param("movieTiming") LocalDateTime movieTiming,
                               @Param("movieId") int movieId,
                               @Param("isActive") boolean isActive);
}
