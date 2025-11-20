package com.aravind.booster.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private DailyQuiz dailyQuiz;

    @Column(columnDefinition = "TEXT")
    private String answersJson;  // store submitted answers

    private int score;

    private LocalDateTime submittedAt = LocalDateTime.now();

    @Column(unique = true)
    private String uniqueUserQuizKey; // user_id + quiz_id (prevents reattempt)
}
