package com.aravind.booster.dto;

public class SubmitQuizResponse {

    private int totalQuestions;
    private int correct;
    private int incorrect;
    private int score;

    public SubmitQuizResponse() {
    }

    public SubmitQuizResponse(int totalQuestions, int correct, int incorrect, int score) {
        this.totalQuestions = totalQuestions;
        this.correct = correct;
        this.incorrect = incorrect;
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
