package com.aravind.booster.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
