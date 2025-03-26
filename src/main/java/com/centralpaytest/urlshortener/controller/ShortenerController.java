package com.centralpaytest.urlshortener.controller;

import com.centralpaytest.urlshortener.exception.RestException;
import com.centralpaytest.urlshortener.services.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/url-shortener")
public class ShortenerController {
    @Autowired
    private ShortenerService shortenerService;

    private void ensureFilledBody(String body, Optional<String> errorMessage) throws RestException {
        if (body == null || body.length() == 0) {
            throw new RestException(HttpStatus.BAD_REQUEST, errorMessage.orElse("You should give a non empty string"));
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createShortenUrl(@RequestBody String url) throws RestException {
        ensureFilledBody(url, Optional.empty());

        try {
            return shortenerService.shortenUrl(url);
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public String getClassicFromShortenUrl(@RequestBody String url) throws RestException {
        ensureFilledBody(url, Optional.empty());

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

    @GetMapping("/all-shorten-urls")
    public List<String> getShortenUrlsFrom(@RequestBody String baseUrl) throws RestException {
        ensureFilledBody(baseUrl, Optional.of("Impossible to retrieve all existing shorten URLs. Please specify as request body the base URL you want to focus"));

        if (!baseUrl.endsWith("/")) { // FIXME verify the end is / or a tld
            throw new RestException(HttpStatus.BAD_REQUEST, "Impossible to retrieve shorten URLs from one containing a path, please remove it");
        }

        return shortenerService.getShortenUrlsFromBase(baseUrl);
    }
}
