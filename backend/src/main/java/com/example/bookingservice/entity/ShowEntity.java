package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "movieShow")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screenId")
    private ScreenEntity screenEntity;

    @ManyToOne
    @JoinColumn(name = "theatreId")
    private TheatreEntity theatreEntity;

    @ManyToOne
    @JoinColumn(name = "movieId")
    private MovieEntity movieEntity;

    private LocalDateTime movieTiming;
    private int durationInMin;

    private LocalDateTime createdAt;
    private boolean isActive;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ShowSeat> seatList;
}
