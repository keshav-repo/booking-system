package com.example.bookingservice.repo;

import com.example.bookingservice.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepo extends JpaRepository<ShowEntity, Integer> {

    @Query("SELECT s FROM ShowEntity s INNER JOIN TheatreEntity t ON t.theatreId = s.theatreEntity.theatreId WHERE s.movieEntity.movieId = :movieId")
    List<ShowEntity> findByMovieId(@Param("movieId") int movieId);

}
