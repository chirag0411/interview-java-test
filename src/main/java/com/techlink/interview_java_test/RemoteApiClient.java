package com.techlink.interview_java_test;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RemoteApiClient {

    private final WebClient webClient;

    @Value("${app.http.response-timeout-ms}")
    private long responseTimeoutMs;

    // ---- GET synchronous----
    public <T> T synchronousGet(String path, Class<T> responseType) {
        return webClient.get()
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(responseTimeoutMs))
                .block();                        // ⬅️ blocks until response or timeout
    }

    public <T> Mono<T> asynchronousGet(String path, Class<T> responseType) {
        return webClient.get()
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(responseTimeoutMs));
    }

    // ---- POST ----
    public <B, T> T post(String path, B body, Class<T> responseType) {
        return webClient.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(responseTimeoutMs))
                .block();
    }

    // ---- PUT ----
    public <B, T> T put(String path, B body, Class<T> responseType) {
        return webClient.put()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(responseTimeoutMs))
                .block();
    }

    // ---- PATCH ----
    public <B, T> T patch(String path, B body, Class<T> responseType) {
        return webClient.patch()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(responseTimeoutMs))
                .block();
    }

    // ---- DELETE ----
    public <T> T delete(String path, Class<T> responseType) {
        return webClient.delete()
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(responseTimeoutMs))
                .block();
    }
}
