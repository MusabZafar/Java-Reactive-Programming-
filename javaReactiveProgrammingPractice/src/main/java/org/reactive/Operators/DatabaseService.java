package org.reactive.Operators;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

public class DatabaseService {

    // Simulated method to fetch user data from PostgreSQL
    public static Mono<String> fetchFromPostgres(String userId) {
        System.out.println("Fetching from PostgreSQL...");
        return Mono.defer(() -> {
            if (userId.equals("123")) {
                return Mono.empty(); // Simulate no data found
            } else if (userId.equals("error")) {
                return Mono.error(new RuntimeException("PostgreSQL connection failed"));
            }
            return Mono.just("Data from PostgreSQL for userId: " + userId);
        });
    }

    // Simulated method to fetch user data from Redis
    public static Mono<String> fetchFromRedis(String userId) {
        System.out.println("Fetching from Redis Cache...");
        return Mono.defer(() -> {
            if (userId.equals("error")) {
                return Mono.error(new RuntimeException("Redis connection failed"));
            }
            return Mono.just("Cached Data from Redis for userId: " + userId);
        });
    }

    // Default fallback data
    public static Mono<String> fallbackData() {
        return Mono.just("Default Fallback Data");
    }
}
