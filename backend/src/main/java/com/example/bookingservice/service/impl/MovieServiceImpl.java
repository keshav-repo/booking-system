package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.MovieDetailRes;
import com.example.bookingservice.dto.MovieShortDetailRes;
import com.example.bookingservice.entity.MovieEntity;
import com.example.bookingservice.repo.MovieRepository;
import com.example.bookingservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<MovieShortDetailRes> getMovies(String city) {
        // todo: validate city
       List<MovieEntity> movieEntityList =  movieRepository.findMoviesByTheatreCity(city);

       return movieEntityList.stream()
               .map(details -> MovieShortDetailRes.builder()
                       .movieId(String.valueOf(details.getMovieId()))
                       .movieName(details.getName())
                       .build())
               .collect(Collectors.toList());
    }

    @Override
    public MovieDetailRes getMoviesDetails(String movieId) {
        MovieEntity movieEntity =  movieRepository.findById(Integer.valueOf(movieId)).get();
        return MovieDetailRes.builder()
                .movieId(String.valueOf(movieEntity.getMovieId()))
                .movieName(movieEntity.getName())
                .build();
    }
}
