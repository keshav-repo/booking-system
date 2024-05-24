package com.example.bookingservice.repo;

import com.example.bookingservice.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    @Query("SELECT m FROM MovieEntity m WHERE m.movieId IN ( " +
            "SELECT s.movieEntity.movieId FROM ShowEntity s " +
            "INNER JOIN s.theatreEntity t " +
            "WHERE t.city = :city )")
    List<MovieEntity> findMoviesByTheatreCity(@Param("city") String city);

}
