package com.aravind.booster.model;

import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DailyQuiz dailyQuiz;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(columnDefinition = "TEXT")
    private String optionsJson;

    private String correctAnswer;

    @ManyToOne
    private Article sourceArticle;

    public Question() {
    }

    public Question(Long id, DailyQuiz dailyQuiz, String text,
                    String optionsJson, String correctAnswer,
                    Article sourceArticle) {
        this.id = id;
        this.dailyQuiz = dailyQuiz;
        this.text = text;
        this.optionsJson = optionsJson;
        this.correctAnswer = correctAnswer;
        this.sourceArticle = sourceArticle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DailyQuiz getDailyQuiz() {
        return dailyQuiz;
    }

    public void setDailyQuiz(DailyQuiz dailyQuiz) {
        this.dailyQuiz = dailyQuiz;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOptionsJson() {
        return optionsJson;
    }

    public void setOptionsJson(String optionsJson) {
        this.optionsJson = optionsJson;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Article getSourceArticle() {
        return sourceArticle;
    }

    public void setSourceArticle(Article sourceArticle) {
        this.sourceArticle = sourceArticle;
    }
}
