package org.reactive.FluxEmittingItemsProgrammatically.DefaultFluxSInk;

import reactor.core.publisher.Flux;

public class CustomSubscriberExample {
    public static void main(String[] args) {
        // Example 1: Using Flux.range with CustomSubscriber
        System.out.println("Example 1: Flux.range with controlled requests");
        Flux<Integer> flux = Flux.range(1, 20); // Create a Flux emitting integers 1 to 20

        // Create a custom subscriber with an initial request of 5 items
        CustomSubscriber<Integer> subscriber1 = new CustomSubscriber<>("Subscriber1", 5);

        // Subscribe the custom subscriber to the Flux
        flux.subscribe(subscriber1);

        // Simulate requesting additional items dynamically
        subscriber1.requestMore(5); // Request 5 more items
        subscriber1.requestMore(10); // Request 10 more items
        subscriber1.cancel(); // Cancel the subscription

        // Example 2: Using Flux.create with limited requests
        System.out.println("\nExample 2: Flux.create with limited requests");
        Flux.create(fluxSink -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Producing: " + i); // Emit each item
                fluxSink.next(i); // Emit the item into the FluxSink
            }
            fluxSink.complete(); // Signal that the emission is complete
        }).subscribe(new CustomSubscriber<>("Subscriber2", 3)); // Subscriber2 requests 3 items

        // Example 3: Using Flux.create with multiple requests
        System.out.println("\nExample 3: Flux.create with dynamic requests");
        Flux.create(fluxSink -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Producing: " + i); // Emit each item
                fluxSink.next(i); // Emit the item into the FluxSink
            }
            fluxSink.complete(); // Signal that the emission is complete
        }).subscribe(new CustomSubscriber<>("Subscriber3", 5, 2)); // Subscriber3 requests 5 items, then 2 more
    }
}
