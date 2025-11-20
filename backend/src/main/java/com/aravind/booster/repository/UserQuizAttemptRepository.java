package com.aravind.booster.repository;

import com.aravind.booster.model.UserQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserQuizAttemptRepository extends JpaRepository<UserQuizAttempt, Long> {

    Optional<UserQuizAttempt> findByUniqueUserQuizKey(String key);

    boolean existsByUniqueUserQuizKey(String key);
}
