package com.aravind.booster.dto;

import java.util.Map;

public class SubmitQuizRequest {

    private String userEmail;
    private String userName;
    private Map<Long, String> answers;

    public SubmitQuizRequest() {
    }

    public SubmitQuizRequest(String userEmail, String userName, Map<Long, String> answers) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.answers = answers;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }
}
