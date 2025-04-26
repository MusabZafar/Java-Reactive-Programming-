package org.reactive.FluxEmittingItemsProgrammatically.FluxSinkExamples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class DynamicApiRequests {
    public static void main(String[] args) {
        // Create a Flux for API requests
        Flux<String> apiRequestFlux = Flux.create(fluxSink -> {
            for (int i = 1; i <= 5; i++) {
                if (fluxSink.isCancelled()) return; // Stop emitting if subscription is canceled
                String apiRequest = "API Request #" + i; // Simulated API request
                System.out.println("Emitting: " + apiRequest);
                fluxSink.next(apiRequest); // Emit API request
                try {
                    Thread.sleep(500); // Simulate delay between requests
                } catch (InterruptedException e) {
                    fluxSink.error(e); // Handle interruption
                }
            }
            fluxSink.complete(); // Signal completion
        });

        // Subscribe to the Flux and process the requests
        apiRequestFlux.subscribe(
                request -> System.out.println("Processing: " + request), // Handle each request
                err -> System.err.println("Error: " + err), // Handle errors
                () -> System.out.println("All requests processed!") // Handle completion
        );
    }
}
