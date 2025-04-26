package org.reactive.Operators;

import reactor.core.publisher.Mono;

public class DatabaseService1 {
    
    // Simulated method to fetch user data from PostgreSQL
    public static Mono<String> fetchFromPostgres(String userId) {
        System.out.println("Fetching from PostgreSQL...");
        // Simulate no data found for userId "123"
        return userId.equals("123") ? Mono.empty() : Mono.just("UserData from PostgreSQL");
    }

    // Simulated method to fetch user data from Redis
    public static Mono<String> fetchFromRedis(String userId) {
        System.out.println("Fetching from Redis Cache...");
        return Mono.just("CachedUserData from Redis");
    }
}
