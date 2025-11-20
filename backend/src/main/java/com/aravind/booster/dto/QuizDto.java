package com.aravind.booster.dto;

import java.time.LocalDate;
import java.util.List;

public class QuizDto {

    private Long id;
    private LocalDate quizDate;
    private List<QuestionDto> questions;

    public QuizDto() {
    }

    public QuizDto(Long id, LocalDate quizDate, List<QuestionDto> questions) {
        this.id = id;
        this.quizDate = quizDate;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(LocalDate quizDate) {
        this.quizDate = quizDate;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
