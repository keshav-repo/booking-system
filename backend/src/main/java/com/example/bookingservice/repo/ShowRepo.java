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

    @Query("SELECT s FROM ShowEntity s INNER JOIN TheatreEntity t ON t.theatreId = s.theatreEntity.theatreId WHERE s.movieEntity.movieId = :movieId AND FUNCTION('DATE', s.movieStartTiming) = CURRENT_DATE")
    List<ShowEntity> findByMovieId(@Param("movieId") int movieId);

    @Query("SELECT s FROM ShowEntity s WHERE s.theatreEntity.theatreId = :theatreId AND s.movieStartTiming = :movieStartTiming AND s.movieEntity.movieId = :movieId AND s.movieEntity.isActive = :isActive")
    List<ShowEntity> findShows(@Param("theatreId") int theatreId,
                               @Param("movieStartTiming") LocalDateTime movieTiming,
                               @Param("movieId") int movieId,
                               @Param("isActive") boolean isActive);

    @Query("SELECT COUNT(s) > 0 FROM ShowEntity s WHERE s.theatreEntity.theatreId = :theatreId AND " +
            "s.screenEntity.screenId = :screenId AND s.movieEntity.movieId = :movieId AND " +
            "((:startTime BETWEEN s.movieStartTiming AND s.movieEndTiming) OR " +
            "(:endTime BETWEEN s.movieStartTiming AND s.movieEndTiming) OR " +
            "(s.movieStartTiming BETWEEN :startTime AND :endTime))")
    boolean hasConflictingShows(@Param("theatreId") Integer theatreId,
                                @Param("screenId") Integer screenId,
                                @Param("movieId") Integer movieId,
                                @Param("startTime") LocalDateTime startTime,
                                @Param("endTime") LocalDateTime endTime);
}
