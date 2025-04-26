package org.reactive.Operators;

import reactor.core.publisher.Mono;

public class OnErrorResumeExample {
    public static void main(String[] args) {
        getDataFromPrimaryService()
                .onErrorResume(error -> {
                    System.out.println("Primary service failed, switching to fallback...");
                    return getDataFromBackupService();
                })
                .subscribe(data -> System.out.println("Received: " + data),
                        error -> System.err.println("Stream failed: " + error),
                        () -> System.out.println("Stream completed!"));
    }

    // Simulate primary service that throws an error
    private static Mono<String> getDataFromPrimaryService() {
        return Mono.error(new RuntimeException("Primary service is down!"));
    }

    // Simulate fallback service
    private static Mono<String> getDataFromBackupService() {
        return Mono.just("Fallback data from backup service");
    }
}

