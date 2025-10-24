package com.techlink.interview_java_test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SimpleWebClientExample {

    private static final WebClient webClient = WebClient.create("https://api.example.com");
    private static final long TIMEOUT_MS = 5000;

    // ---- GET ----
    public static <T> T get(String path, Class<T> responseType) {
        return webClient.get()
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(TIMEOUT_MS))
                .block();
    }

    // ---- POST ----
    public static <B, T> T post(String path, B body, Class<T> responseType) {
        return webClient.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(TIMEOUT_MS))
                .block();
    }

    // ---- PUT ----
    public static <B, T> T put(String path, B body, Class<T> responseType) {
        return webClient.put()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(TIMEOUT_MS))
                .block();
    }

    // ---- PATCH ----
    public static <B, T> T patch(String path, B body, Class<T> responseType) {
        return webClient.patch()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(TIMEOUT_MS))
                .block();
    }

    // ---- DELETE ----
    public static <T> T delete(String path, Class<T> responseType) {
        return webClient.delete()
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(TIMEOUT_MS))
                .block();
    }

    // demo main
    public static void main(String[] args) {
        var result = get("/v1/customers/123", String.class);
        System.out.println(result);
    }
}
