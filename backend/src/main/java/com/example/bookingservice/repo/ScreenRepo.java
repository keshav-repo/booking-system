package com.example.bookingservice.repo;

import com.example.bookingservice.entity.ScreenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepo extends JpaRepository<ScreenEntity, Integer> {
}
