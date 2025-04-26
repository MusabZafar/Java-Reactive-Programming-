package org.reactive.Operators;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

//public class ProductService {
//
//    // Simulated REST API call (Primary Source)
//    public static Mono<String> fetchFromApi() {
//        return Mono.fromSupplier(() -> {
//            System.out.println("Calling REST API...");
//            // Simulate delay of 3 seconds (slower than timeout threshold)
//            Util.sleepMillis(3000);
//            return "Product data from REST API";
//        });
//    }
//
//    // Simulated Redis Cache (Fallback 1)
//    public static Mono<String> fetchFromRedisCache() {
//        return Mono.fromSupplier(() -> {
//            System.out.println("Fetching data from Redis Cache...");
//            Util.sleepMillis(500); // Fast response from cache
//            return "Product data from Redis Cache";
//        });
//    }
//
//    // Simulated PostgreSQL Database (Fallback 2)
//    public static Mono<String> fetchFromDatabase() {
//        return Mono.fromSupplier(() -> {
//            System.out.println("Querying PostgreSQL Database...");
//            Util.sleepMillis(2000); // Simulated database delay
//            return "Product data from PostgreSQL Database";
//        });
//    }
//}
