package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "movieShow")
@Entity
@Getter
@Setter
@EqualsAndHashCode
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatreId")
    private TheatreEntity theatreEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieId")
    private MovieEntity movieEntity;

    private LocalDateTime movieStartTiming;
    private LocalDateTime movieEndTiming;

    private int durationInMin;

    private LocalDateTime createdAt;
    private boolean isActive;

    @OneToMany( mappedBy = "showEntity", fetch = FetchType.LAZY)
    private List<ShowSeat> seatList;

    @OneToMany(mappedBy = "showEntity", fetch = FetchType.LAZY)
    private List<BookingEntity> bookingEntityList;
}
