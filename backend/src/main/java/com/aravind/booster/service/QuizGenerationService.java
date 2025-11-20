package com.aravind.booster.service;

import com.aravind.booster.model.Article;
import com.aravind.booster.model.DailyQuiz;
import com.aravind.booster.model.Question;
import com.aravind.booster.repository.ArticleRepository;
import com.aravind.booster.repository.DailyQuizRepository;
import com.aravind.booster.repository.QuestionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizGenerationService {

    private final DailyQuizRepository dailyQuizRepository;
    private final QuestionRepository questionRepository;
    private final ArticleRepository articleRepository;
    private final ObjectMapper objectMapper;

    public QuizGenerationService(DailyQuizRepository dailyQuizRepository,
                                 QuestionRepository questionRepository,
                                 ArticleRepository articleRepository,
                                 ObjectMapper objectMapper) {
        this.dailyQuizRepository = dailyQuizRepository;
        this.questionRepository = questionRepository;
        this.articleRepository = articleRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Generate a quiz for a given date from the articles of that date.
     * If quiz already exists for that date, returns the existing one.
     */
    public DailyQuiz generateQuizForDate(LocalDate date) {
        // If quiz already exists, don't regenerate
        Optional<DailyQuiz> existing = dailyQuizRepository.findByQuizDate(date);
        if (existing.isPresent()) {
            return existing.get();
        }

        List<Article> articles = articleRepository.findByPublishedDate(date);

        // We need at least 4 articles to make 1 question with 3 distractors
        if (articles.size() < 4) {
            throw new IllegalStateException("Not enough articles to generate quiz for " + date);
        }

        // Shuffle to randomize
        Collections.shuffle(articles);

        // Create the quiz
        DailyQuiz quiz = new DailyQuiz();
        quiz.setQuizDate(date);
        quiz.setGeneratedAt(LocalDateTime.now());
        quiz = dailyQuizRepository.save(quiz);

        // Make up to 10 questions
        int maxQuestions = Math.min(10, articles.size());
        List<Question> questions = new ArrayList<>();

        // Pre-collect all titles for distractors
        List<String> allTitles = articles.stream()
                .map(Article::getTitle)
                .filter(t -> t != null && !t.isBlank())
                .collect(Collectors.toList());

        for (int i = 0; i < maxQuestions; i++) {
            Article article = articles.get(i);
            String correctTitle = article.getTitle();
            if (correctTitle == null || correctTitle.isBlank()) {
                continue;
            }

            // Build distractor titles (other headlines from same day)
            List<String> distractors = allTitles.stream()
                    .filter(t -> !t.equals(correctTitle))
                    .collect(Collectors.toList());

            if (distractors.size() < 1) {
                continue; // not enough to build options
            }

            Collections.shuffle(distractors);

            List<String> options = new ArrayList<>();
            options.add(correctTitle); // correct
            for (int j = 0; j < 3 && j < distractors.size(); j++) {
                options.add(distractors.get(j));
            }

            Collections.shuffle(options); // randomize order

            Question q = new Question();
            q.setDailyQuiz(quiz);
            q.setSourceArticle(article);
            q.setText("Which of the following was a news headline today?");
            q.setCorrectAnswer(correctTitle);

            try {
                String optionsJson = objectMapper.writeValueAsString(options);
                q.setOptionsJson(optionsJson);
            } catch (Exception e) {
                // If JSON conversion fails, skip this question
                continue;
            }

            questions.add(q);
        }

        questionRepository.saveAll(questions);
        quiz.setQuestions(questions);
        return quiz;
    }
}
