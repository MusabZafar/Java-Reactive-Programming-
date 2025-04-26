package org.reactive.Operators;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

public class ResilientService {
    public static void main(String[] args) {
        String userId = "error"; // Simulate a failure scenario

        DatabaseService.fetchFromPostgres(userId)
                // Retry operation up to 3 times in case of failure
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .doBeforeRetry(signal -> System.out.println("Retrying... Attempt: " + signal.totalRetriesInARow())))
                // Switch to Redis cache if PostgreSQL returns no data
                .switchIfEmpty(DatabaseService.fetchFromRedis(userId))
                // Fallback to default data if Redis also fails
                .onErrorResume(error -> {
                    System.out.println("Error encountered: " + error.getMessage());
                    return DatabaseService.fallbackData();
                })
                .subscribe(
                        data -> System.out.println("Received: " + data), // OnNext
                        error -> System.err.println("Final Error: " + error), // OnError
                        () -> System.out.println("Stream Completed!") // OnComplete
                );
    }
}
