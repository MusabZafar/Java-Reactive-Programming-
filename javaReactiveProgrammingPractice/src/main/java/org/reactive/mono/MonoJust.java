package org.reactive.mono;

import org.reactive.CustomPublisherSubscriberSubscription.subscriber.SubscriberImpl;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public class MonoJust {

    public static void main(String[] args) {
        // Step 1: Create a Mono from an already available value
        var mono = Mono.just("Hello World");

        // Step 2: Create a subscriber (custom implementation)
        var subscriber = new SubscriberImpl();

        // Step 3: Subscribe the subscriber to the Mono
        mono.subscribe(subscriber);

        // Step 4: Request 10 items (Mono will emit at most 1)
        subscriber.getSubscription().request(10);

        // Step 5: Additional requests and cancellation
        subscriber.getSubscription().request(10); // No effect
        subscriber.getSubscription().cancel();    // No effect

        // Step 6: Pass the Mono to another method for processing
        save(Mono.just("Hello World"));
    }

    // Method to accept and process a Publisher (e.g., saving to a database)
    private static void save(Publisher<String> publisher) {
        // Example of saving the publisher (e.g., DB operations)
    }
}