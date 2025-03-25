package com.centralpaytest.urlshortener.controller;

import com.centralpaytest.urlshortener.exception.RestException;
import com.centralpaytest.urlshortener.services.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url-shortener")
public class ShortenerController {
    @Autowired
    private ShortenerService shortenerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createShortenUrl(@RequestBody String url) throws RestException {
        if (url.length() == 0) {
            throw new RestException(HttpStatus.BAD_REQUEST, "You should give a non empty string");
        }

        try {
            return shortenerService.shortenUrl(url);
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public String getClassicFromShortenUrl(@RequestBody String url) throws RestException {
        if (url.length() == 0) {
            throw new RestException(HttpStatus.BAD_REQUEST, "You should give a non empty string");
        }

        String retrievedUrl;
        try {
            retrievedUrl = shortenerService.getOriginalUrlFrom(url);
        } catch (Exception e) {
            throw new RestException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        if (retrievedUrl == null) {
            throw new RestException(HttpStatus.NOT_FOUND, String.format("URL %s is not corresponding to a valid shorten URL", url));
        }
        return retrievedUrl;
    }
}
