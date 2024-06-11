package com.watermelon.dateapp.domain;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
public class Tendency {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tendency_id")
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
}
