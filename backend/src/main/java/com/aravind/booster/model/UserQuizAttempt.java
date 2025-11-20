package com.aravind.booster.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_quiz_attempt")
public class UserQuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private DailyQuiz dailyQuiz;

    @Column(columnDefinition = "TEXT")
    private String answersJson;

    private int score;

    private LocalDateTime submittedAt = LocalDateTime.now();

    @Column(unique = true)
    private String uniqueUserQuizKey;

    public UserQuizAttempt() {
    }

    public UserQuizAttempt(Long id, User user, DailyQuiz dailyQuiz,
                           String answersJson, int score,
                           LocalDateTime submittedAt, String uniqueUserQuizKey) {
        this.id = id;
        this.user = user;
        this.dailyQuiz = dailyQuiz;
        this.answersJson = answersJson;
        this.score = score;
        this.submittedAt = submittedAt;
        this.uniqueUserQuizKey = uniqueUserQuizKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DailyQuiz getDailyQuiz() {
        return dailyQuiz;
    }

    public void setDailyQuiz(DailyQuiz dailyQuiz) {
        this.dailyQuiz = dailyQuiz;
    }

    public String getAnswersJson() {
        return answersJson;
    }

    public void setAnswersJson(String answersJson) {
        this.answersJson = answersJson;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getUniqueUserQuizKey() {
        return uniqueUserQuizKey;
    }

    public void setUniqueUserQuizKey(String uniqueUserQuizKey) {
        this.uniqueUserQuizKey = uniqueUserQuizKey;
    }
}
