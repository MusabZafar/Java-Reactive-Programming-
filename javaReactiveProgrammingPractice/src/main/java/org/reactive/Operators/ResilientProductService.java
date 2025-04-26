//package org.reactive.Operators;
//
//import reactor.core.publisher.Mono;
//import reactor.util.retry.Retry;
//
//import java.time.Duration;
//
//public class ResilientProductService {
//    public static void main(String[] args) {
//
//        fetchProductDataWithResiliency()
//                .timeout(Duration.ofSeconds(2), ProductService.fetchFromRedisCache()) // Primary timeout with Redis fallback
//                .timeout(Duration.ofSeconds(1), ProductService.fetchFromDatabase())  // Redis timeout with DB fallback
//                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1))) // Retry transient failures up to 2 times with backoff
//                .onErrorReturn("Default Product Data") // Final fallback if everything fails
//                .subscribe(
//                        data -> System.out.println("Received: " + data), // OnNext
//                        error -> System.err.println("Error: " + error),   // OnError
//                        () -> System.out.println("Stream Completed!")    // OnComplete
//                );
//
//        // Keep the main thread alive to observe async operations
//        Util.sleepSeconds(10);
//    }
//
//    // Service to fetch product data with timeout and fallback
//    private static Mono<String> fetchProductDataWithResiliency() {
//        return ProductService.fetchFromApi(); // Primary data source
//    }
//}
