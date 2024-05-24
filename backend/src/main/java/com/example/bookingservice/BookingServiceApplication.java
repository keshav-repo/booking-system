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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BookingServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }

//    @Autowired
//    private EntityManager entityManager;

//
//    @Autowired
//    private MovieRepository movieRepository;
//
//    @Autowired
//    private TheatreRepo theatreRepo;
//
//    @Autowired
//    private ShowRepo showRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        MovieEntity movieEntity = MovieEntity.builder()
//                .name("Avenger")
//                .build();
//
//        entityManager.persist(movieEntity);

//        MovieEntity movieEntity = entityManager.find(MovieEntity.class, 1);
//
//        TheatreEntity theatreEntity = TheatreEntity.builder()
//                .name("Inox 2")
//                .city("Bengaluru")
//                .state("Karnataka")
//                .country("India")
//                .addressLine1("address line 1")
//                .build();
//
//        entityManager.persist(theatreEntity);
//
//        ScreenEntity screenEntity = ScreenEntity.builder()
//                .screenName("Screen 2")
//                .theatreEntity(theatreEntity)
//                .build();
//
//        entityManager.persist(screenEntity);
//
//        ShowEntry show = ShowEntry.builder()
//                .createdAt(LocalDateTime.now())
//                .durationInMin(180)
//                .movieEntity(movieEntity)
//                .screenEntity(screenEntity)
//                .theatreEntity(theatreEntity)
//                .movieTiming(LocalDateTime.now().plusHours(2))
//                .build();
//
//        entityManager.persist(show);
    }
}
