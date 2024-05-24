package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Table(name = "screen")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int screenId;

    private String screenName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatreId")
    private TheatreEntity theatreEntity;

    @OneToMany(mappedBy = "screenEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Seat> seatList;
}
