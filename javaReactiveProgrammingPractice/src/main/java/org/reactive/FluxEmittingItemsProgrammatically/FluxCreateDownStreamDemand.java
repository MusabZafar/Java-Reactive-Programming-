package org.reactive.FluxEmittingItemsProgrammatically;

import org.reactive.CustomPublisherSubscriberSubscription.subscriber.SubscriberImpl;
import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Flux create does not check the downsream demand by default it is by design!
*/
public class FluxCreateDownStreamDemand {
    public static final Logger log = LoggerFactory.getLogger(FluxCreateDownStreamDemand.class);

    public static void main(String[] args) throws InterruptedException {

        var subscriber = new SubscriberImpl(); // Custom subscriber for handling backpressure

        // Create a Flux that emits 10 names
        Flux.<String>create(
                fluxSink -> {
                    for (int i = 0; i < 10; i++) {
                        var name = Util.faker().name().fullName(); // Generate a random name
                        log.info("Emitting item {}", name); // Log the emitted item
                        fluxSink.next(name); // Emit the name to the FluxSink
                    }
                    fluxSink.complete(); // Signal completion after emitting all items
                }
        ).subscribe(subscriber); // Subscribe the custom subscriber to this Flux

        /*
         * Problem: Even if the subscriber cancels immediately or doesn't request any items,
         * the producer generates all the items upfront. This is the default behavior of `Flux.create`.
         * The subscriber might not receive all the items if it cancels early or limits its requests.
         */

        // Cancel the subscription immediately without requesting items
        subscriber.getSubscription().cancel();

        // Simulate delayed downstream requests
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2); // Request the first 2 items
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2); // Request 2 more items
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel(); // Cancel the subscription again
    }

    /**
     * Demonstrates the default behavior of Flux.create where the producer emits all items upfront,
     * regardless of downstream demand. Overproduction may occur if the subscriber doesn't request all items.
     */
    private static void produceEarly() throws InterruptedException {
        var subscriber = new SubscriberImpl(); // Create a custom subscriber

        // Create a Flux that emits items upfront
        Flux.<String>create(fluxSink -> {
            for (int i = 0; i < 10; i++) { // Produce 10 items
                var name = Util.faker().name().firstName(); // Generate a random name
                log.info("generated: {}", name); // Log the emitted name
                fluxSink.next(name); // Emit the name to the sink
            }
            fluxSink.complete(); // Signal the completion of the stream
        }).subscribe(subscriber); // Subscribe the custom subscriber to this Flux

        // Demonstrating subscriber behavior:
        Util.sleepSeconds(2); // Delay for 2 seconds
        subscriber.getSubscription().request(2); // Request 2 items from the subscription
        Util.sleepSeconds(2); // Delay for 2 seconds
        subscriber.getSubscription().request(2); // Request another 2 items
        Util.sleepSeconds(2); // Delay for 2 seconds
        subscriber.getSubscription().cancel(); // Cancel the subscription
        subscriber.getSubscription().request(2); // Try requesting 2 more items (has no effect)
    }

    /**
     * Demonstrates a more efficient approach where items are produced on demand.
     * The producer emits items only when explicitly requested by the subscriber, respecting backpressure.
     */
    private static void produceOnDemand() throws InterruptedException {
        var subscriber = new SubscriberImpl(); // Create a custom subscriber

        // Create a Flux that produces items on demand
        Flux.<String>create(fluxSink -> {

            // Define onRequest behavior to produce items based on downstream demand
            fluxSink.onRequest(request -> {
                for (int i = 0; i < request && !fluxSink.isCancelled(); i++) { // Emit items only when requested
                    var name = Util.faker().name().firstName(); // Generate a random name
                    log.info("generated: {}", name); // Log the emitted name
                    fluxSink.next(name); // Emit the name to the sink
                }
            });

        }).subscribe(subscriber); // Subscribe the custom subscriber to this Flux

        // Demonstrating subscriber behavior:
        Util.sleepSeconds(2); // Delay for 2 seconds
        subscriber.getSubscription().request(2); // Request 2 items from the subscription
        Util.sleepSeconds(2); // Delay for 2 seconds
        subscriber.getSubscription().request(2); // Request another 2 items
        Util.sleepSeconds(2); // Delay for 2 seconds
        subscriber.getSubscription().cancel(); // Cancel the subscription
        subscriber.getSubscription().request(2); // Try requesting 2 more items (has no effect)
    }
}
