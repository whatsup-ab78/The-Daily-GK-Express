package com.aravind.booster.repository;

import com.aravind.booster.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByPublishedDate(LocalDate date);

    List<Article> findByCategory(String category);
}
