package com.centralpaytest.urlshortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url-shortener")
public class ShortenerController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createShortenUrl(String url) {
        return "the shorten url created";
    }

    @GetMapping
    public String getClassicFromShortenUrl(String url) {
        return "the real URL";
    }
}
