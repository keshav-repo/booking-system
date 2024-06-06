package com.example.bookingservice.repo;

import com.example.bookingservice.entity.AuthorityEntity;
import com.example.bookingservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepo extends JpaRepository<AuthorityEntity, Integer> {
    AuthorityEntity findByRole(Role role);
}
