package com.watermelon.dateapp.domain.user;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.*;

@Entity
public class Question {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "question_id")
    private Long id;
    private String question;
}
