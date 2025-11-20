package com.aravind.booster.service;

import com.aravind.booster.dto.QuestionDto;
import com.aravind.booster.dto.QuizDto;
import com.aravind.booster.dto.SubmitQuizRequest;
import com.aravind.booster.dto.SubmitQuizResponse;
import com.aravind.booster.model.*;
import com.aravind.booster.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final DailyQuizRepository dailyQuizRepository;
    private final UserRepository userRepository;
    private final UserQuizAttemptRepository userQuizAttemptRepository;
    private final ObjectMapper objectMapper;
    private final QuizGenerationService quizGenerationService;

    public QuizService(DailyQuizRepository dailyQuizRepository,
                       UserRepository userRepository,
                       UserQuizAttemptRepository userQuizAttemptRepository,
                       ObjectMapper objectMapper,
                       QuizGenerationService quizGenerationService) {
        this.dailyQuizRepository = dailyQuizRepository;
        this.userRepository = userRepository;
        this.userQuizAttemptRepository = userQuizAttemptRepository;
        this.objectMapper = objectMapper;
        this.quizGenerationService = quizGenerationService;
    }

    private LocalDate todayInIndia() {
        return LocalDate.now(ZoneId.of("Asia/Kolkata"));
    }

    /**
     * Get today's quiz (generate if missing) as DTO.
     */
    public QuizDto getTodayQuiz() {
        LocalDate today = todayInIndia();
        DailyQuiz quiz = dailyQuizRepository.findByQuizDate(today)
                .orElseGet(() -> quizGenerationService.generateQuizForDate(today));

        return toQuizDto(quiz);
    }

    private QuizDto toQuizDto(DailyQuiz quiz) {
        List<QuestionDto> questionDtos = new ArrayList<>();

        if (quiz.getQuestions() != null) {
            for (Question q : quiz.getQuestions()) {
                List<String> options;
                try {
                    options = objectMapper.readValue(
                            q.getOptionsJson(),
                            new TypeReference<List<String>>() {}
                    );
                } catch (Exception e) {
                    options = Collections.emptyList();
                }

                QuestionDto dto = new QuestionDto(q.getId(), q.getText(), options);
                questionDtos.add(dto);
            }
        }

        return new QuizDto(quiz.getId(), quiz.getQuizDate(), questionDtos);
    }

    /**
     * Submit answers for a quiz.
     * - Creates or finds a user by email
     * - Prevents multiple attempts per user+quiz
     * - Calculates score
     * - Stores UserQuizAttempt
     */
    public SubmitQuizResponse submitQuiz(Long quizId, SubmitQuizRequest request) {
        if (request.getUserEmail() == null || request.getUserEmail().isBlank()) {
            throw new IllegalArgumentException("userEmail is required");
        }

        DailyQuiz quiz = dailyQuizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Find or create user
        User user = userRepository.findByEmail(request.getUserEmail())
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(request.getUserEmail());
                    u.setName(request.getUserName() != null ? request.getUserName() : "GK User");
                    u.setPassword(""); // no real auth yet
                    return userRepository.save(u);
                });

        String uniqueKey = user.getId() + ":" + quiz.getId();

        if (userQuizAttemptRepository.existsByUniqueUserQuizKey(uniqueKey)) {
            throw new IllegalStateException("You have already attempted this quiz.");
        }

        Map<Long, String> answers = request.getAnswers() != null
                ? request.getAnswers()
                : Collections.emptyMap();

        int totalQuestions = quiz.getQuestions() != null ? quiz.getQuestions().size() : 0;
        int correct = 0;

        if (quiz.getQuestions() != null) {
            for (Question q : quiz.getQuestions()) {
                String given = answers.get(q.getId());
                if (given != null && given.equals(q.getCorrectAnswer())) {
                    correct++;
                }
            }
        }

        int incorrect = totalQuestions - correct;
        int score = correct; // simple scoring: 1 point per correct

        // Store attempt
        UserQuizAttempt attempt = new UserQuizAttempt();
        attempt.setUser(user);
        attempt.setDailyQuiz(quiz);
        attempt.setScore(score);
        attempt.setUniqueUserQuizKey(uniqueKey);

        try {
            String answersJson = objectMapper.writeValueAsString(answers);
            attempt.setAnswersJson(answersJson);
        } catch (Exception e) {
            attempt.setAnswersJson("{}");
        }

        userQuizAttemptRepository.save(attempt);

        return new SubmitQuizResponse(totalQuestions, correct, incorrect, score);
    }
}
