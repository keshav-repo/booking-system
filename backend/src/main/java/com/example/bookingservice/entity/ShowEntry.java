package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "movieShow")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screenId")
    private ScreenEntity screenEntity;

    @OneToOne
    @JoinColumn(name = "theatreId")
    private TheatreEntity theatreEntity;

    @OneToOne
    @JoinColumn(name = "movieId")
    private MovieEntity movieEntity;

    private LocalDate movieTiming;
    private int durationInMin;

    private LocalDate createdAt;
    private boolean isActive;
}
