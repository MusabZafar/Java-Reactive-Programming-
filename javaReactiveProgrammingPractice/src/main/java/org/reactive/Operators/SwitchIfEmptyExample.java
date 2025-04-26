package org.reactive.Operators;

import reactor.core.publisher.Flux;

public class SwitchIfEmptyExample {
    public static void main(String[] args) {
        getDataFromPrimaryService()
                .switchIfEmpty(getDataFromBackupService()) // Switch to backup if primary emits no data
                .subscribe(data -> System.out.println("Received: " + data),
                           error -> System.err.println("Error: " + error),
                           () -> System.out.println("Stream Completed!"));
    }

    // Primary service returns an empty stream
    private static Flux<String> getDataFromPrimaryService() {
        return Flux.empty(); // Simulate no data returned
    }

    // Backup service emits fallback data
    private static Flux<String> getDataFromBackupService() {
        return Flux.just("Backup Data 1", "Backup Data 2", "Backup Data 3");
    }
}
