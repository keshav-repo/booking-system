package com.example.bookingservice.repo;

import com.example.bookingservice.entity.TheatreEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@Sql(scripts = "/data/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class TheatreRepoTest {
    @Autowired
    private TheatreRepo theatreRepo;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @Order(1)
    public void saveTheatreTest(){

        TheatreEntity theatre = TheatreEntity.builder().name("Inox Yelahanka")
                .build();

        theatreRepo.save(theatre);

        Assertions.assertTrue(theatre.getTheatreId()!=0);
    }

    @Test
    @Order(2)
    public void checkATheatreTest(){
      List<TheatreEntity> theatreList =  theatreRepo.findAll();
      Assertions.assertEquals(6, theatreList.size());
    }

    @Test
    public void findByCityTest(){

//       List<TheatreEntity>  bengaluruTheatres =  theatreRepo.findByCity("Bengaluru");
//        Assertions.assertEquals(4, bengaluruTheatres.size());

    }
}
