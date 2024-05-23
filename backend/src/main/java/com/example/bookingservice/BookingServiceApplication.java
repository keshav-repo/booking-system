package com.example.bookingservice;

import com.example.bookingservice.entity.*;
import com.example.bookingservice.repo.MovieRepository;
import com.example.bookingservice.repo.ShowRepo;
import com.example.bookingservice.repo.TheatreRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BookingServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepo theatreRepo;

    @Autowired
    private ShowRepo showRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        /*
        MovieEntity movieEntity = MovieEntity.builder()
                .name("Bahubali")
                .build();

        entityManager.persist(movieEntity);

        TheatreEntity theatreEntity = TheatreEntity.builder()
                .name("Inox")
                .city("Bengaluru")
                .state("Karnataka")
                .country("India")
                .addressLine1("address line 1")
                .build();

        entityManager.persist(theatreEntity);

        ScreenEntity screenEntity = ScreenEntity.builder()
                .screenName("Screen 1")
                .theatreEntity(theatreEntity)
                .build();

        entityManager.persist(screenEntity);

        Show show = Show.builder()
                .createdAt(LocalDate.now())
                .durationInMin(180)
                .movieEntity(movieEntity)
                .screenEntity(screenEntity)
                .theatreEntity(theatreEntity)
                .build();

        entityManager.persist(show);

         */

//        List<MovieEntity> movieEntityList = movieRepository.findMoviesByTheatreCity("Bengaluru");
//
//        System.out.println(movieEntityList);

        List<ShowEntry> theatreEntityList =  showRepo.findByMovieId(1);
        System.out.println(theatreEntityList);



    }
}
