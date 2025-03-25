package com.centralpaytest.urlshortener.services;

import org.springframework.stereotype.Service;

// cf. https://stackoverflow.com/questions/742013/how-do-i-create-a-url-shortener
@Service
public class StringEncoder {
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private final int BASE = ALPHABET.length();

    public String encode(int i) {
        if (i == 0) return String.valueOf(ALPHABET.charAt(0));

        StringBuilder s = new StringBuilder();
        while (i > 0)
        {
            s.append(ALPHABET.charAt(i % BASE));
            i = i / BASE;
        }
        return s.reverse().toString();
    }

    public int decode(String toDecode) {
        String[] listOfLetters = toDecode.split("");
        int i = 0;

        for (String letter: listOfLetters) {
            i = (i * BASE) + ALPHABET.indexOf(letter);
        }

        return i;
    }
}
