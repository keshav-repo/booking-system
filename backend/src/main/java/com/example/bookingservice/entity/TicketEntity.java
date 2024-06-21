package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    private LocalDateTime bookingTime;

    @OneToOne
    @JoinColumn(name = "movieId", unique = false)
    private MovieEntity movieEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showId", unique=false)
    private ShowEntity showEntity;

    @OneToMany(mappedBy = "ticketEntity", fetch = FetchType.LAZY)
    private List<ShowSeat> showSeatList;
}
