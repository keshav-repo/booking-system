package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "pricing")
@Entity
@Data
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
