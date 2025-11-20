package com.aravind.booster.controller;

import com.aravind.booster.model.Article;
import com.aravind.booster.service.NewsFetchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    private final NewsFetchService newsFetchService;


    public NewsController(NewsFetchService newsFetchService) {
        this.newsFetchService = newsFetchService;
    }

    @GetMapping("/api/news/test-fetch")
    public List<Article> testFetch() {
        return newsFetchService.fetchAndSaveArticlesForToday();
    }
}
