package com.aravind.booster.scheduler;

import com.aravind.booster.model.Article;
import com.aravind.booster.model.DailyQuiz;
import com.aravind.booster.service.NewsFetchService;
import com.aravind.booster.service.QuizGenerationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class DailyQuizScheduler {

    private final NewsFetchService newsFetchService;
    private final QuizGenerationService quizGenerationService;

    public DailyQuizScheduler(NewsFetchService newsFetchService,
                              QuizGenerationService quizGenerationService) {
        this.newsFetchService = newsFetchService;
        this.quizGenerationService = quizGenerationService;
    }

    /**
     * Runs every day at 02:00 AM IST.
     * 1. Fetches yesterday's articles
     * 2. Generates a quiz for that date
     */
    @Scheduled(cron = "0 0 2 * * *", zone = "Asia/Kolkata")
    public void runDailyJob() {
        ZoneId zone = ZoneId.of("Asia/Kolkata");
        LocalDate yesterday = LocalDate.now(zone).minusDays(1);

        System.out.println("🕑 DailyQuizScheduler started for date: " + yesterday);

        List<Article> articles = newsFetchService.fetchAndSaveArticlesForDate(yesterday);
        System.out.println("Fetched and saved " + articles.size() + " articles for " + yesterday);

        try {
            DailyQuiz quiz = quizGenerationService.generateQuizForDate(yesterday);
            int count = quiz.getQuestions() != null ? quiz.getQuestions().size() : 0;
            System.out.println("✅ Generated quiz for " + yesterday + " with " + count + " questions.");
        } catch (Exception e) {
            System.out.println("⚠️ Failed to generate quiz for " + yesterday + ": " + e.getMessage());
        }
    }
}
