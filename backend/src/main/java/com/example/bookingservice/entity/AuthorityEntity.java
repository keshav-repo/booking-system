package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "userAuthority")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorityId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> userList;
}
