package org.reactive.Operators;

import reactor.core.publisher.Mono;

public class MultiServiceErrorHandling {
    public static void main(String[] args) {
        getDataFromServiceA()
                .onErrorResume(error -> {
                    System.out.println("Service A failed, switching to Service B...");
                    return getDataFromServiceB();
                })
                .onErrorReturn("Fallback value") // General fallback
                .onErrorMap(error -> new RuntimeException("Final transformation: " + error.getMessage()))
                .subscribe(
                        data -> System.out.println("Received: " + data),
                        error -> System.err.println("Error: " + error)
                );
    }

    private static Mono<String> getDataFromServiceA() {
        return Mono.error(new RuntimeException("Service A is down"));
    }

    private static Mono<String> getDataFromServiceB() {
        return Mono.error(new IllegalArgumentException("Service B failed"));
    }
}
