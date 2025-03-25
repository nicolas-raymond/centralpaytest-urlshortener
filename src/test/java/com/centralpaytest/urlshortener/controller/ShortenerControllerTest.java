package com.centralpaytest.urlshortener.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortenerControllerTest {
    private static final String API_ROOT = "http://localhost:8080/url-shortener";

    @Test
    public void post_without_body_returns_400() {
        Response response = RestAssured.post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
        //assertEquals("You should give a non empty string", response.getBody().asString()); FIXME empty message => issue with custom exception?
    }
    @Test
    public void post_with_empty_body_returns_400() {
        Response response = RestAssured.given().body("").post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
        //assertEquals("You should give a non empty string", response.getBody().asString()); FIXME empty message => issue with custom exception?
    }
    @Test
    public void post_with_our_domain_returns_specified_500() {
        Response response = RestAssured.given().body("https://short.fr/").post(API_ROOT);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
        assertEquals("Impossible to shorten this URL", response.getBody().asString());
    }
    @Test
    public void post_with_url_as_body_returns_201() {
        Response response = RestAssured.given().body("https://www.google.com/").post(API_ROOT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void get_without_url_returns_400() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
    @Test
    public void get_with_a_classic_url_returns_400() {
        Response response = RestAssured.given().body("https://www.google.com/").get(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
    @Test
    public void get_with_a_fake_shorten_url_returns_404() {
        // When
        Response response = RestAssured.given().body("https://short.fr/w").get(API_ROOT);
        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
        assertEquals("URL https://short.fr/w is not corresponding to a valid shorten URL", response.getBody().asString());
    }
    @Test
    public void get_with_a_shorten_url_returns_the_original_url() {
        // Given
        RestAssured.given().body("https://www.google.com/").post(API_ROOT); // TODO use mocks instead of integration test?
        // When
        Response response = RestAssured.given().body("https://short.fr/w").get(API_ROOT);
        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("https://www.google.com", response.getBody().asString());
    }
}
