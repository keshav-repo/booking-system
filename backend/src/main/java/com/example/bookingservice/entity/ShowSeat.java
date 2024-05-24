package com.example.bookingservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "showSeat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showId", nullable = false)
    private ShowEntity showEntity;

    @ManyToOne
    @JoinColumn(name = "seatId", nullable = false)
    private Seat seat;

    private boolean isAvailable;
    private BigDecimal price;
}
