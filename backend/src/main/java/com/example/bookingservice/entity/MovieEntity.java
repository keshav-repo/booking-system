package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "movie")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    private String name;
    private String language;
    private String about;
    private String url;
    private boolean isActive;
}
