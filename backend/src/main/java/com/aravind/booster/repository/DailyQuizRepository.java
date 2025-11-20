package com.aravind.booster.repository;

import com.aravind.booster.model.DailyQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyQuizRepository extends JpaRepository<DailyQuiz, Long> {

    Optional<DailyQuiz> findByQuizDate(LocalDate date);

    boolean existsByQuizDate(LocalDate date);
}
