package org.reactive.flux;

import reactor.core.publisher.Flux;

public class FluxDeferExample {
    public static void main(String[] args) {
        // Create a Flux that generates a new value for each subscription
        Flux<String> deferredFlux = Flux.defer(() -> {
            System.out.println("Creating a new Flux...");
            return Flux.just("Value generated at: " + System.currentTimeMillis());
        });

        // First subscription
        deferredFlux.subscribe(value -> System.out.println("Subscriber 1 received: " + value));

        // Wait for a moment before the second subscription
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Second subscription
        deferredFlux.subscribe(value -> System.out.println("Subscriber 2 received: " + value));
    }
}
