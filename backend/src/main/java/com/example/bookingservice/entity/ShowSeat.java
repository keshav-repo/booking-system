package com.example.bookingservice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "showSeat")
@Getter
@Setter
@EqualsAndHashCode
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatId", nullable = false)
    private Seat seat;

    private boolean isAvailable;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "ticketId", nullable = true)
    private TicketEntity ticketEntity;

    @Version
    private long version;
}
