package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "screen")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int screenId;

    private String screenName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatreId")
    private TheatreEntity theatreEntity;

    @OneToMany(mappedBy = "screenEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Seat> seatList;
}
