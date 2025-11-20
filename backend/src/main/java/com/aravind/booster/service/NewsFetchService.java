package com.aravind.booster.service;

import com.aravind.booster.model.Article;
import com.aravind.booster.repository.ArticleRepository;
import com.aravind.booster.util.RssClient;
import org.springframework.stereotype.Service;
import com.rometools.rome.feed.synd.SyndEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsFetchService {

    private final ArticleRepository articleRepository;
    private final RssClient rssClient;

    // ✅ Normal constructor (Spring will use this to inject dependencies)
    public NewsFetchService(ArticleRepository articleRepository, RssClient rssClient) {
        this.articleRepository = articleRepository;
        this.rssClient = rssClient;
    }

    private final List<String> rssFeeds = List.of(
            "https://www.thehindu.com/news/national/feeder/default.rss",
            "https://www.thehindu.com/sport/feeder/default.rss",
            "https://www.thehindu.com/news/international/feeder/default.rss"
    );

    public List<Article> fetchAndSaveArticlesForToday() {
        List<Article> saved = new ArrayList<>();

        for (String feed : rssFeeds) {
            List<SyndEntry> entries = rssClient.fetchArticles(feed);

            for (SyndEntry entry : entries) {
                Article article = new Article();
                article.setSource("The Hindu");
                article.setTitle(entry.getTitle());
                article.setContent(entry.getDescription() != null ? entry.getDescription().getValue() : "");
                article.setUrl(entry.getLink());
                article.setCategory("general");
                article.setPublishedDate(LocalDate.now());

                saved.add(articleRepository.save(article));
            }
        }

        return saved;
    }
}
