package com.aravind.booster.util;

import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.feed.synd.*;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RssClient {

    public List<SyndEntry> fetchArticles(String feedUrl) {
        List<SyndEntry> entries = new ArrayList<>();

        try {
            URL url = new URL(feedUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            entries.addAll(feed.getEntries());
        } catch (Exception e) {
            System.out.println("RSS Fetch Error: " + e.getMessage());
        }

        return entries;
    }
}
