package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "pricing")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pricingId;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatreId")
    private TheatreEntity theatreEntity;

    private BigDecimal price;
}
