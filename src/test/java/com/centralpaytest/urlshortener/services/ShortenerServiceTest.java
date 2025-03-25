package com.centralpaytest.urlshortener.services;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShortenerServiceTest {
    ShortenerService tested = new ShortenerService(new StringEncoder()); // not necessary to use TestConfiguration yet

    @Test
    public void shortenUrl_should_have_output_shorter_than_input() {
        // Given
        String input = "https://www.google.com";
        // When
        try {
            String output = tested.shortenUrl(input);
            // Then
            assertTrue(output.length() < input.length());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void shortenUrl_should_have_output_starting_with_custom_domain() {
        try {
            // When
            String output = tested.shortenUrl("https://www.google.com");
            // Then
            assertTrue(output.startsWith("https://short.fr/"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void shortenUrl_throws_exception_if_input_has_same_lenght_as_custom_domain() {
        assertThrowsExactly(Exception.class, () -> tested.shortenUrl("https://short.fr/"), "Impossible to shorten this URL");
    }
}
