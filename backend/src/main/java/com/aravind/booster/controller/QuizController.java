package com.aravind.booster.controller;

import com.aravind.booster.dto.QuizDto;
import com.aravind.booster.dto.SubmitQuizRequest;
import com.aravind.booster.dto.SubmitQuizResponse;
import com.aravind.booster.model.DailyQuiz;
import com.aravind.booster.service.QuizGenerationService;
import com.aravind.booster.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizGenerationService quizGenerationService;
    private final QuizService quizService;

    public QuizController(QuizGenerationService quizGenerationService,
                          QuizService quizService) {
        this.quizGenerationService = quizGenerationService;
        this.quizService = quizService;
    }

    // OLD admin/test endpoint – keep it
    @GetMapping("/generate-today")
    public Map<String, Object> generateTodayQuiz() {
        LocalDate today = LocalDate.now();
        DailyQuiz quiz = quizGenerationService.generateQuizForDate(today);

        Map<String, Object> response = new HashMap<>();
        response.put("quizId", quiz.getId());
        response.put("quizDate", quiz.getQuizDate());
        response.put("questionCount", quiz.getQuestions() != null ? quiz.getQuestions().size() : 0);

        return response;
    }

    // ✅ NEW: Get today's quiz (full questions + options)
    @GetMapping("/today")
    public QuizDto getTodayQuiz() {
        return quizService.getTodayQuiz();
    }

    // ✅ NEW: Submit answers
    @PostMapping("/{quizId}/submit")
    public SubmitQuizResponse submitQuiz(@PathVariable Long quizId,
                                         @RequestBody SubmitQuizRequest request) {
        return quizService.submitQuiz(quizId, request);
    }
}
