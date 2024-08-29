package com.watermelon.dateapp.domain.user;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class Question {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "question_id")
    private Long id;
    private String question;
}
