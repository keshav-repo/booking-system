package com.example.bookingservice.repo;

import com.example.bookingservice.entity.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TheatreRepo extends JpaRepository<TheatreEntity, Integer> {

}
