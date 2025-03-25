package com.centralpaytest.urlshortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShortenerService {
    final String URL_STARTER = "https://short.fr/";
    Map<Integer, String> IDS_TO_RETRIEVE_URL = new HashMap<>(); // TODO replace by database
    StringEncoder stringEncoder;

    @Autowired
    ShortenerService(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
        IDS_TO_RETRIEVE_URL.put(URL_STARTER.length(), URL_STARTER);
    }

    /**
     * @param longUrl url to shorten
     * @return a string representing the input as a shorter string
     */
    public String shortenUrl(String longUrl) throws Exception {
        int idUrl = longUrl.length(); // FIXME use length is a bad solution (better will be with unique database ID)
        if (IDS_TO_RETRIEVE_URL.get(idUrl) == null) {
            IDS_TO_RETRIEVE_URL.put(idUrl, longUrl);
        } else {
            throw new Exception("Impossible to shorten this URL");
        }
        return URL_STARTER.concat(stringEncoder.encode(idUrl));
    }
}
