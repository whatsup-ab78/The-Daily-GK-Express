package com.aravind.booster.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String url;

    private String category;

    private LocalDate publishedDate;

    private LocalDateTime fetchedAt = LocalDateTime.now();

    // ===== Constructors =====
    public Article() {
    }

    // (Optional) full constructor
    public Article(Long id, String source, String title, String content,
                   String url, String category, LocalDate publishedDate,
                   LocalDateTime fetchedAt) {
        this.id = id;
        this.source = source;
        this.title = title;
        this.content = content;
        this.url = url;
        this.category = category;
        this.publishedDate = publishedDate;
        this.fetchedAt = fetchedAt;
    }

    // ===== Getters & Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public LocalDateTime getFetchedAt() {
        return fetchedAt;
    }

    public void setFetchedAt(LocalDateTime fetchedAt) {
        this.fetchedAt = fetchedAt;
    }
}
