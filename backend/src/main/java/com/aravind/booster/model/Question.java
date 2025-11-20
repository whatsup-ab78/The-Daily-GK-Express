package com.aravind.booster.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DailyQuiz dailyQuiz;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(columnDefinition = "TEXT")
    private String optionsJson;  // store options as JSON string

    private String correctAnswer;

    @ManyToOne
    private Article sourceArticle;
}
