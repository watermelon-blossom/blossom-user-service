package com.watermelon.dateapp.domain;

import jakarta.persistence.*;

import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    private UserName userName;

    @OneToMany(mappedBy = "user")
    private List<UserTendency> userTendency;
}
