package org.reactive.Operators;

import reactor.core.publisher.Mono;

public class OnErrorMapExample {
    public static void main(String[] args) {
        Mono.just("user123")
                .flatMap(OnErrorMapExample::getUserDetails)
                .onErrorMap(error -> new CustomServiceException("Service failed", error))
                .subscribe(
                        data -> System.out.println("Received: " + data),
                        error -> System.err.println("Error: " + error)
                );
    }

    // Simulated method to fetch user details
    private static Mono<String> getUserDetails(String userId) {
        return Mono.error(new IllegalArgumentException("Invalid user ID"));
    }

    // Custom exception class for better context
    static class CustomServiceException extends RuntimeException {
        public CustomServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
