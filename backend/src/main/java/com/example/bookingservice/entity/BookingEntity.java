package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "booking")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    private LocalDateTime bookingTime;

    @OneToMany(mappedBy = "bookingEntity", fetch = FetchType.LAZY)
    private List<ShowSeat> showSeatList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showId", nullable = false)
    private ShowEntity showEntity;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", unique = false)
    private User user;
}
