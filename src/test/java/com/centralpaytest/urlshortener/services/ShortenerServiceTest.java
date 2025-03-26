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
            assertTrue(output.length() <= input.length());
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
            assertTrue(output.startsWith("https://shrt.fr/"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void shortenUrl_throws_exception_if_input_has_same_lenght_as_custom_domain() {
        assertThrowsExactly(Exception.class, () -> tested.shortenUrl("https://shrt.fr/"), "Impossible to shorten this URL");
    }

    @Test
    public void getOriginalUrlFrom_a_short_url_returns_correctly_the_base_url() throws Exception {
        // Given
        String baseUrl = "https://www.google.com";
        String shortUrl = tested.shortenUrl(baseUrl); // "https://short.fr/097aio" TODO a real unit test when database
        // When
        String result = tested.getOriginalUrlFrom(shortUrl);
        // Then
        assertEquals("https://www.google.com", result);
    }
    @Test
    public void getOriginalUrlFrom_fails_when_input_is_not_a_shorten_url() {
        assertThrowsExactly(Exception.class, () -> tested.getOriginalUrlFrom("anything"), "The given URL was not generated by this service");
    }
}
