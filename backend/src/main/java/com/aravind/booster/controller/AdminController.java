package com.aravind.booster.controller;

import com.aravind.booster.model.Article;
import com.aravind.booster.model.DailyQuiz;
import com.aravind.booster.service.NewsFetchService;
import com.aravind.booster.service.QuizGenerationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final NewsFetchService newsFetchService;
    private final QuizGenerationService quizGenerationService;

    public AdminController(NewsFetchService newsFetchService,
                           QuizGenerationService quizGenerationService) {
        this.newsFetchService = newsFetchService;
        this.quizGenerationService = quizGenerationService;
    }

    /**
     * Manually trigger "daily job" for today (for testing).
     * 1. Fetches today's articles
     * 2. Generates today's quiz
     */
    @GetMapping("/run-today")
    public Map<String, Object> runTodayJob() {
        ZoneId zone = ZoneId.of("Asia/Kolkata");
        LocalDate today = LocalDate.now(zone);

        List<Article> articles = newsFetchService.fetchAndSaveArticlesForDate(today);
        DailyQuiz quiz = quizGenerationService.generateQuizForDate(today);

        Map<String, Object> response = new HashMap<>();
        response.put("date", today.toString());
        response.put("articlesSaved", articles.size());
        response.put("quizId", quiz.getId());
        response.put("questionCount", quiz.getQuestions() != null ? quiz.getQuestions().size() : 0);

        return response;
    }
}
